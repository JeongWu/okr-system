package com.eximbay.okr.dto.objectiveRelation;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.keyResult.KeyResultDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ObjectiveRelationDto extends AbstractAuditableDto {

    private Integer relationSeq;
    private ObjectiveDto objective;
    private KeyResultDto topKeyResult;
    private ObjectiveDto relatedObjective;
    private String useFlag;
}
