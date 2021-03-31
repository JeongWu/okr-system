package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.evaluationobjective.EvaluationObjectiveDto;

import java.util.List;

public interface IEvaluationObjectiveService extends IService<EvaluationObjectiveDto, Integer> {
    List<EvaluationObjectiveDto> findByObjectivesSeqIn(List<Integer> objectivesSeq);
}
