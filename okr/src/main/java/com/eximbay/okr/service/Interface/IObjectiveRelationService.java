package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.objectiverelation.ObjectiveRelationDto;

import java.util.List;

public interface IObjectiveRelationService extends IService<ObjectiveRelationDto, Integer> {
    List<ObjectiveRelationDto> findByObjectiveSeqInAndRelatedObjectiveNotNull(List<Integer> in);
}
