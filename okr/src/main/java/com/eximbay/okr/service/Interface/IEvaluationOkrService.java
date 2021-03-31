package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.evaluationokr.EvaluationOkrDto;
import com.eximbay.okr.entity.EvaluationOkr;

import java.util.Optional;

public interface IEvaluationOkrService extends IService<EvaluationOkrDto, Integer> {
    Optional<EvaluationOkr> findByQuarterStringAndObjectiveType(String quarter, String objectiveType);
}
