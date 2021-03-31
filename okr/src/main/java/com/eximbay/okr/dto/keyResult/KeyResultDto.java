package com.eximbay.okr.dto.keyresult;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class KeyResultDto extends AbstractAuditableDto {

    private Integer keyResultSeq;
    private Integer objectiveSeq;
    private String objectiveLevel;
    private ObjectiveDto objective;
    private String keyResult;
    private String beginDate;
    private String endDate;
    private String taskType;
    private String taskMetric;
    private String taskIndicator;
    private Integer proportion;
    private Integer progress;
    private Instant latestUpdateDt;
    private String closeFlag;
    private String closeJustification;
    private String closeDate;
    private Integer totalValue;

    // field not in entity class
    private String justification;

    public boolean isClosed() {
        return FlagOption.isYes(closeFlag);
    }

    //fake setter to be used by mapper
    public void setObjectiveSeq(Objective objective) {
        this.objectiveSeq = objective.getObjectiveSeq();
    }
}
