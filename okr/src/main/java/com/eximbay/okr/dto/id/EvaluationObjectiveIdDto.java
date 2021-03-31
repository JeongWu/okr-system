package com.eximbay.okr.dto.id;

import com.eximbay.okr.dto.evaluationokr.EvaluationOkrDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import lombok.Data;

@Data
public class EvaluationObjectiveIdDto {

    EvaluationOkrDto evaluationOkr;
    ObjectiveDto objective;
}
