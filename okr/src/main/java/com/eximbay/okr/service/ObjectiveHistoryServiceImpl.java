package com.eximbay.okr.service;

import com.eximbay.okr.dto.objectiveHistory.ObjectiveHistoryDto;
import com.eximbay.okr.entity.ObjectiveHistory;
import com.eximbay.okr.exception.RestUserException;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.repository.ObjectiveHistoryRepository;
import com.eximbay.okr.service.Interface.IObjectiveHistoryService;
import com.eximbay.okr.utils.StopWatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@Service
@Transactional
public class ObjectiveHistoryServiceImpl implements IObjectiveHistoryService {
    private final ObjectiveHistoryRepository objectiveHistoryRepository;
    private final MapperFacade mapper;

    @Override
    public List<ObjectiveHistoryDto> findAll() {
        List<ObjectiveHistory> objectiveHistories = objectiveHistoryRepository.findAll();
        return mapper.mapAsList(objectiveHistories, ObjectiveHistoryDto.class);
    }

    @Override
    public Optional<ObjectiveHistoryDto> findById(Integer id) {
        Optional<ObjectiveHistory> objectiveHistory = objectiveHistoryRepository.findById(id);
        return objectiveHistory.map(m-> mapper.map(m, ObjectiveHistoryDto.class));
    }

    @Override
    public void remove(ObjectiveHistoryDto objectiveHistoryDto) {
        ObjectiveHistory objectiveHistory = mapper.map(objectiveHistoryDto, ObjectiveHistory.class);
        objectiveHistoryRepository.delete(objectiveHistory);
    }

    @Override
    public ObjectiveHistoryDto save(ObjectiveHistoryDto objectiveHistoryDto) {
        ObjectiveHistory objectiveHistory = mapper.map(objectiveHistoryDto, ObjectiveHistory.class);
        objectiveHistory = objectiveHistoryRepository.save(objectiveHistory);
        return mapper.map(objectiveHistory, ObjectiveHistoryDto.class);
    }

    @Override
    public void saveAll(List<ObjectiveHistoryDto> historyDtos) {
        List<ObjectiveHistory> histories = mapper.mapAsList(historyDtos, ObjectiveHistory.class);
        objectiveHistoryRepository.saveAll(histories);
    }
}
