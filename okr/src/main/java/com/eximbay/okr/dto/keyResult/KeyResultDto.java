package com.eximbay.okr.dto.keyResult;

import com.eximbay.okr.listener.AbstractAuditableDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import lombok.Data;

import java.time.Instant;

@Data
public class KeyResultDto extends AbstractAuditableDto {

    private Integer keyResultSeq;
    private ObjectiveDto objective;
    private String keyResult;
    private String beginDate;
    private String endDate;
    private String taskType;
    private String taskMetric;
    private String taskIndicator;
    private Integer proportion;
    private Integer progress;
    private Instant lastUpdatedDate;
    private String closeFlag;
    private String closeJustification;
    private String closeDate;
}
