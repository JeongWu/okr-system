package com.eximbay.okr.dto.evaluationobjective;

import com.eximbay.okr.dto.id.EvaluationObjectiveIdDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EvaluationObjectiveDto extends AbstractAuditableDto {

    private EvaluationObjectiveIdDto evaluationObjectiveId;
    private String selfContribution;
    private String selfPerformanceReview;
    private Integer selfScore;
    private String selfScoreLevel;
    private String firstAppraiserFeedback;
    private String firstAppraiserContribution;
    private String firstAppraiserQualitative;
    private Integer firstAppraiserScore;
    private String firstAppraiserScoreLevel;
    private String secondAppraiserFeedback;
    private String secondAppraiserContribution;
    private String secondAppraiserQualitative;
    private Integer secondAppraiserScore;
    private String secondAppraiserScoreLevel;
}
