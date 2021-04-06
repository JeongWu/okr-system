package com.eximbay.okr.dto.evaluationschedule;

import lombok.Data;

@Data
public class EvaluationScheduleDto {
    private Integer scheduleSeq;
    private String evaluationType;
    private Integer evaluationStep;
    private String selfReviewBeginDate;
    private String selfReviewEndDate;
    private String firstReviewBeginDate;
    private String firstReviewEndDate;
    private String secondReviewBeginDate;
    private String secondReviewEndDate;
    private String reviewBeginDate;
    private String reviewEndDate;
}
