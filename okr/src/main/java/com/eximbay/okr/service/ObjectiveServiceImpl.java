package com.eximbay.okr.service;

import com.eximbay.okr.constant.*;
import com.eximbay.okr.dto.*;
import com.eximbay.okr.dto.objective.UpdateObjectivePriorityDto;
import com.eximbay.okr.dto.objective.UpdateObjectivePriorityRequest;
import com.eximbay.okr.dto.objectiveHistory.ObjectiveHistoryDto;
import com.eximbay.okr.entity.*;
import com.eximbay.okr.exception.RestUserException;
import com.eximbay.okr.model.objective.CompanyObjectiveOkrModel;
import com.eximbay.okr.repository.*;
import com.eximbay.okr.service.Interface.*;
import com.eximbay.okr.service.specification.ObjectiveQuery;
import com.eximbay.okr.utils.StopWatch;
import lombok.*;
import ma.glasnost.orika.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ObjectiveServiceImpl implements IObjectiveService {
    private final ObjectiveRepository objectiveRepository;
    private final ObjectiveQuery objectiveQuery;
    private final IObjectiveHistoryService objectiveHistoryService;
    private final MapperFacade mapper;

    @Override
    public List<ObjectiveDto> findAll() {
        List<Objective> objectives = objectiveRepository.findAll();
        return mapper.mapAsList(objectives, ObjectiveDto.class);
    }

    @Override
    public Optional<ObjectiveDto> findById(Integer id) {
        Optional<Objective> objective = objectiveRepository.findById(id);
        return objective.map(m-> mapper.map(m, ObjectiveDto.class));
    }

    @Override
    public void remove(ObjectiveDto objectiveDto) {
        Objective objective = mapper.map(objectiveDto, Objective.class);
        objectiveRepository.delete(objective);
    }

    @Override
    public ObjectiveDto save(ObjectiveDto objectiveDto) {
        Objective objective = mapper.map(objectiveDto, Objective.class);
        objective = objectiveRepository.save(objective);
        return mapper.map(objective, ObjectiveDto.class);
    }

    @Override
    public List<ObjectiveDto> findAllInUse() {
        List<Objective> objectives = objectiveRepository.findByCloseFlag(FlagOption.N);
        return mapper.mapAsList(objectives, ObjectiveDto.class);
    }

    @Override
    public List<String> findAllQuarters() {
        return objectiveRepository.findAllQuarters();
    }

    @Override
    public List<ObjectiveDto> findAllInQuarter(String quarterString) {
        try {
            Integer year = Integer.valueOf(quarterString.substring(0,4));
            Integer quarter = Integer.valueOf(quarterString.substring(quarterString.length() - 2, quarterString.length() - 1));
            List<Objective> objectives = objectiveRepository.findAll(
                    objectiveQuery.findInYear(year)
                    .and(objectiveQuery.findInQuarter(quarter))
            );
            return mapper.mapAsList(objectives, ObjectiveDto.class);
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<CompanyObjectiveOkrModel> findAllCompanyObjectiveOkrInQuarter(String quarterString) {
        try {
            Integer year = Integer.valueOf(quarterString.substring(0,4));
            Integer quarter = Integer.valueOf(quarterString.substring(quarterString.length() - 2, quarterString.length() - 1));
            List<Objective> objectives = objectiveRepository.findAll(
                    objectiveQuery.findInYear(year)
                            .and(objectiveQuery.findByCompany())
                            .and(objectiveQuery.findInQuarter(quarter))
                    , Sort.by(Objective_.PRIORITY)
            );
            return mapper.mapAsList(objectives, CompanyObjectiveOkrModel.class);
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public void updateObjectivePriority(UpdateObjectivePriorityRequest request) {
        Map<Integer, Integer> updateValueMap = request.getData().stream()
                .collect(Collectors.toMap(UpdateObjectivePriorityDto::getObjectiveSeq, UpdateObjectivePriorityDto::getPriority));

        List<Objective> objectives = objectiveRepository.findAll(objectiveQuery.findByObjectiveSeqIn(updateValueMap.keySet()));
        List<ObjectiveHistoryDto> objectiveHistories = new ArrayList<>();

        objectives.forEach(o->{
            o.setPriority(updateValueMap.get(o.getObjectiveSeq()));
            ObjectiveHistoryDto history = mapper.map(o, ObjectiveHistoryDto.class);
            history.setObjectiveObject(mapper.map(o, ObjectiveDto.class));
            history.setJustification(AppConst.DEFAULT_CHANGE_PRIORITY_JUSTIFICATION);
            objectiveHistories.add(history);
        });
        objectiveRepository.saveAll(objectives);
        objectiveHistoryService.saveAll(objectiveHistories);
    }
}
