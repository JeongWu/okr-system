package com.eximbay.okr.service;

import com.eximbay.okr.dto.keyResult.KeyResultDto;
import com.eximbay.okr.entity.KeyResult;
import com.eximbay.okr.model.keyResult.KeyResultCompanyOkrModel;
import com.eximbay.okr.repository.KeyResultRepository;
import com.eximbay.okr.service.Interface.IKeyResultService;
import com.eximbay.okr.service.specification.KeyResultQuery;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KeyResultServiceImpl implements IKeyResultService {
    private final KeyResultRepository keyResultRepository;
    private final KeyResultQuery keyResultQuery;
    private final MapperFacade mapper;

    @Override
    public List<KeyResultDto> findAll() {
        List<KeyResult> keyResults = keyResultRepository.findAll();
        return mapper.mapAsList(keyResults, KeyResultDto.class);
    }

    @Override
    public Optional<KeyResultDto> findById(Integer id) {
        Optional<KeyResult> keyResult = keyResultRepository.findById(id);
        return keyResult.map(m-> mapper.map(m, KeyResultDto.class));
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
    public List<KeyResultCompanyOkrModel> findByObjectiveSeqIn(List<Integer> in) {
        List<KeyResult> keyResults = keyResultRepository.findAll(
                keyResultQuery.findByObjectiveSeqIn(in)
        );
        return mapper.mapAsList(keyResults, KeyResultCompanyOkrModel.class);
    }
}
