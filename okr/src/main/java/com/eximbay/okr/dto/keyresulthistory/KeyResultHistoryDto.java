package com.eximbay.okr.dto.keyresulthistory;

import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class KeyResultHistoryDto extends AbstractAuditableDto {

    private Integer historySeq;
    private KeyResultDto keyResultObject;
    private Integer objectiveSeq;
    private String objectiveLevel;
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
    private String justification;
}
