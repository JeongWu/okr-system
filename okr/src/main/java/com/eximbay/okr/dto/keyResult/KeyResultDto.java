package com.eximbay.okr.dto.keyResult;

import com.eximbay.okr.dto.ObjectiveDto;
import lombok.Data;

import java.time.Instant;

@Data
public class KeyResultDto {

    private Integer keyResultSeq;
    private ObjectiveDto objective;
    private String keyResult;
    private String beginDate;
    private String endDate;
    private String taskType;
    private String taskMetric;
    private String taskIndicator;
    private int progress = 0;
    private Instant lastUpdatedDate;
    private String closeFlag;
    private String closeJustification;
    private String closeDate;
    private Integer likes;
}
