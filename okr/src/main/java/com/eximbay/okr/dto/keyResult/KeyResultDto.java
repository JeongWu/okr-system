package com.eximbay.okr.dto.keyresult;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.listener.AbstractAuditableDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import lombok.Data;

import java.time.Instant;

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
    private Integer proportion = 0;
    private Integer progress;
    private Instant lastUpdatedDate;
    private String closeFlag;
    private String closeJustification;
    private String closeDate;

    // field not in entity class
    private String justification;

    public boolean isClosed() {
        return FlagOption.isYes(closeFlag);
    }

    //fake setter to be used by mapper
    public void setObjective(Objective objective) {
        this.objectiveSeq = objective.getObjectiveSeq();
    }
}
