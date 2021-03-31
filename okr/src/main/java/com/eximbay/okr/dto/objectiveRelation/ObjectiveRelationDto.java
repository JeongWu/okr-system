package com.eximbay.okr.dto.objectiverelation;

import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
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
