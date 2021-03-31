package com.eximbay.okr.service;

import com.eximbay.okr.dto.objectiverelation.ObjectiveRelationDto;
import com.eximbay.okr.entity.ObjectiveRelation;
import com.eximbay.okr.repository.ObjectiveRelationRepository;
import com.eximbay.okr.service.Interface.IObjectiveRelationService;
import com.eximbay.okr.service.specification.ObjectiveRelationQuery;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ObjectiveRelationServiceImpl implements IObjectiveRelationService {

    private final MapperFacade mapper;
    private final ObjectiveRelationRepository objectiveRelationRepository;
    private final ObjectiveRelationQuery objectiveRelationQuery;

    @Override
    public List<ObjectiveRelationDto> findAll() {
        List<ObjectiveRelation> objectiveRelations = objectiveRelationRepository.findAll();
        return mapper.mapAsList(objectiveRelations, ObjectiveRelationDto.class);
    }

    @Override
    public Optional<ObjectiveRelationDto> findById(Integer id) {
        Optional<ObjectiveRelation> objectiveRelation = objectiveRelationRepository.findById(id);
        return objectiveRelation.map(m -> mapper.map(m, ObjectiveRelationDto.class));
    }

    @Override
    public void remove(ObjectiveRelationDto objectiveRelationDto) {
        ObjectiveRelation objectiveRelation = mapper.map(objectiveRelationDto, ObjectiveRelation.class);
        objectiveRelationRepository.delete(objectiveRelation);
    }

    @Override
    public ObjectiveRelationDto save(ObjectiveRelationDto objectiveRelationDto) {
        ObjectiveRelation objectiveRelation = mapper.map(objectiveRelationDto, ObjectiveRelation.class);
        objectiveRelation = objectiveRelationRepository.save(objectiveRelation);
        return mapper.map(objectiveRelation, ObjectiveRelationDto.class);
    }

    @Override
    public List<ObjectiveRelationDto> findByObjectiveSeqInAndRelatedObjectiveNotNull(List<Integer> in) {
        List<ObjectiveRelation> objectiveRelations = objectiveRelationRepository.findAll(
                objectiveRelationQuery.findActiveObjectiveRelation()
                        .and(objectiveRelationQuery.findByObjectiveSeqIn(in))
                        .and(objectiveRelationQuery.findByRelatedObjectiveNotNull())
        );
        return mapper.mapAsList(objectiveRelations, ObjectiveRelationDto.class);
    }
}
