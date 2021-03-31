package com.eximbay.okr.service;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.entity.KeyResult;
import com.eximbay.okr.entity.KeyResultHistory;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.exception.RestUserException;
import com.eximbay.okr.model.keyResult.KeyResultViewOkrModel;
import com.eximbay.okr.repository.KeyResultHistoryRepository;
import com.eximbay.okr.repository.KeyResultRepository;
import com.eximbay.okr.repository.ObjectiveRepository;
import com.eximbay.okr.service.Interface.IKeyResultService;
import com.eximbay.okr.service.specification.KeyResultQuery;
import com.eximbay.okr.utils.DateTimeUtils;
import com.eximbay.okr.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class KeyResultServiceImpl implements IKeyResultService {

    private final MapperFacade mapper;
    private final KeyResultRepository keyResultRepository;
    private final KeyResultHistoryRepository keyResultHistoryRepository;
    private final ObjectiveRepository objectiveRepository;
    private final KeyResultQuery keyResultQuery;

    @Override
    public List<KeyResultDto> findAll() {
        List<KeyResult> keyResults = keyResultRepository.findAll();
        return mapper.mapAsList(keyResults, KeyResultDto.class);
    }

    @Override
    public Optional<KeyResultDto> findById(Integer id) {
        Optional<KeyResult> keyResult = keyResultRepository.findById(id);
        return keyResult.map(m -> mapper.map(m, KeyResultDto.class));
    }

    @Override
    public void remove(KeyResultDto keyResultDto) {
        KeyResult keyResult = mapper.map(keyResultDto, KeyResult.class);
        keyResultRepository.delete(keyResult);
    }

    @Override
    public KeyResultDto save(KeyResultDto keyResultDto) {
        KeyResult keyResult = mapper.map(keyResultDto, KeyResult.class);
        keyResult = keyResultRepository.save(keyResult);
        return mapper.map(keyResult, KeyResultDto.class);
    }

    @Override
    public List<KeyResultViewOkrModel> findByObjectiveSeqIn(List<Integer> in) {
        List<KeyResult> keyResults = keyResultRepository.findAll(
                keyResultQuery.findByObjectiveSeqIn(in)
        );
        List<KeyResultViewOkrModel> result = mapper.mapAsList(keyResults, KeyResultViewOkrModel.class);
        formatKeyResultViewOkrModel(result);
        return result;
    }

    private void formatKeyResultViewOkrModel(List<KeyResultViewOkrModel> keyResults) {
        keyResults.forEach(e -> e.setShortenKeyResult(StringUtils.shortenString(e.getKeyResult(), AppConst.KR_MAX_LENGTH)));
    }

    @Override
    public List<KeyResultDto> findByObjectSeq(List<Integer> in) {
        List<KeyResult> keyResults = keyResultRepository.findAll(
            keyResultQuery.findByObjectiveSeqIn(in)
    );
    return mapper.mapAsList(keyResults, KeyResultDto.class);
    }

    public List<KeyResultDto> findByCloseFlagAndObjectiveSeq(String closeFlag, int objectiveSeq) {
        List<KeyResult> keyResults = keyResultRepository.findByCloseFlagAndObjectiveSeq(closeFlag, objectiveSeq);
        return mapper.mapAsList(keyResults, KeyResultDto.class);
    }

    @Override
    public void updateKeyResult(KeyResultDto keyResultDto) {
        KeyResult originalKeyResult = keyResultRepository.findById(keyResultDto.getKeyResultSeq()).orElseThrow(() -> new RestUserException("Key result not found"));
        originalKeyResult.setKeyResult(keyResultDto.getKeyResult());
        if (!originalKeyResult.isClosed() && keyResultDto.isClosed()) {
            originalKeyResult.setCloseDate(DateTimeUtils.todayDBString());
            originalKeyResult.setCloseJustification(keyResultDto.getCloseJustification());
        }
        originalKeyResult.setCloseFlag(keyResultDto.getCloseFlag());
        originalKeyResult.setObjectiveLevel(keyResultDto.getObjectiveLevel());
        originalKeyResult.setKeyResult(keyResultDto.getKeyResult());
        originalKeyResult.setTaskType(keyResultDto.getTaskType());
        originalKeyResult.setTaskMetric(keyResultDto.getTaskMetric());
        originalKeyResult.setTaskIndicator(keyResultDto.getTaskIndicator());
        originalKeyResult.setProportion(keyResultDto.getProportion());
        KeyResultHistory keyResultHistory = mapper.map(originalKeyResult, KeyResultHistory.class);
        keyResultHistory.setJustification(keyResultDto.getJustification());
        keyResultHistory.setKeyResultObject(originalKeyResult);
        keyResultRepository.save(originalKeyResult);
        keyResultHistoryRepository.save(keyResultHistory);
    }

    @Override
    public void addKeyResult(KeyResultDto keyResultDto) {
        Objective objective = objectiveRepository.findById(keyResultDto.getObjectiveSeq()).orElseThrow(() -> new RestUserException("Invalid objective seq " + keyResultDto.getObjectiveSeq()));
        KeyResult keyResult = mapper.map(keyResultDto, KeyResult.class);
        keyResult.setObjective(objective);
        keyResult.setBeginDate(objective.getBeginDate());
        keyResult.setEndDate(objective.getEndDate());
        if (keyResultDto.isClosed()) {
            keyResult.setCloseDate(DateTimeUtils.todayDBString());
        }
        KeyResultHistory keyResultHistory = mapper.map(keyResult, KeyResultHistory.class);
        keyResultHistory.setJustification(keyResultDto.getJustification());
        keyResultHistory.setKeyResultObject(keyResult);
        keyResultRepository.save(keyResult);
        keyResultHistoryRepository.save(keyResultHistory);
    }

    @Override
    public void updateOrAddKeyResults(List<KeyResultDto> keyResultUpdateDtos) {
        for (KeyResultDto keyResultDto : keyResultUpdateDtos) {
            if (keyResultDto.getKeyResultSeq() == null) {
                this.addKeyResult(keyResultDto);
            } else {
                this.updateKeyResult(keyResultDto);
            }
        }
    }


}
