package com.eximbay.okr.dto.objectiveRelation;

import com.eximbay.okr.dto.ObjectiveDto;
import com.eximbay.okr.dto.keyResult.KeyResultDto;
import lombok.Data;

@Data
public class ObjectiveRelationDto {

    private Integer relationSeq;
    private ObjectiveDto objective;
    private KeyResultDto topKeyResult;
    private ObjectiveDto relatedObjective;
    private String useFlag;
}
