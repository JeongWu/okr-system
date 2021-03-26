package com.eximbay.okr.service;

import com.eximbay.okr.constant.*;
import com.eximbay.okr.dto.*;
import com.eximbay.okr.entity.*;
import com.eximbay.okr.repository.*;
import com.eximbay.okr.service.Interface.*;
import lombok.*;
import ma.glasnost.orika.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@AllArgsConstructor
public class ObjectiveServiceImpl implements IObjectiveService {
    private final ObjectiveRepository objectiveRepository;
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

}
