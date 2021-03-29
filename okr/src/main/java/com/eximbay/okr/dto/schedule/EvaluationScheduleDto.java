package com.eximbay.okr.dto.schedule;

import lombok.Data;

@Data
public class EvaluationScheduleDto {
    private String evalutaionType;
    private int evaluationStep;
    private String selfReviewBeginDate;
    private String selfReviewEndDate;
    private String firstReviewBeginDate;
    private String firstReviewEndDate;
    private String secondReviewBeginDate;
    private String secondReviewEndDate;
    private String reviewBeginDate;
    private String reviewEndDate;
}
