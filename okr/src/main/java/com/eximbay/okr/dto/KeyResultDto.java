package com.eximbay.okr.dto;

import java.time.Instant;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.enumeration.TaskIndicator;
import com.eximbay.okr.enumeration.TaskMetric;
import com.eximbay.okr.enumeration.TaskType;
import com.eximbay.okr.dto.objective.ObjectiveDto;

import lombok.Data;

@Data
public class KeyResultDto {

    private Integer keyResultSeq;
    private ObjectiveDto objective;
    private String objectiveLevel;
    private String keyResult;
    private Integer beginDate;
    private Integer endDate;
    private TaskType taskType;
    private TaskMetric taskMetric;
    private TaskIndicator taskIndicator;
    private Integer progress = 0;
    private Instant lastUpdateDate;
    private String closeFlag = FlagOption.N;
    private String cloaseJustification;
    private String closeDate;
    private Integer totalValue =0;
    
}
