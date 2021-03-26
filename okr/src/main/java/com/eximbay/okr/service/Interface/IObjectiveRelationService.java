package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.objectiveRelation.ObjectiveRelationDto;

import java.util.List;

public interface IObjectiveRelationService extends ISerivce<ObjectiveRelationDto, Integer> {
    List<ObjectiveRelationDto> findByObjectiveSeqInAndRelatedObjectiveNotNull(List<Integer> in);
}
