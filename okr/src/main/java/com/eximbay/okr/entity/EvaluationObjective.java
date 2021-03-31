package com.eximbay.okr.entity;

import com.eximbay.okr.entity.id.EvaluationObjectiveId;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "evaluation_objective")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EvaluationObjective extends AbstractAuditable {

    @EmbeddedId
    private EvaluationObjectiveId evaluationObjectiveId;

    @Column(name = "SELF_CONTRIBUTION", length = 20, nullable = false)
    private String selfContribution = "L";

    @Column(name = "SELF_PERFORMANCE_REVIEW", length = 1000)
    private String selfPerformanceReview;

    @Column(name = "SELF_SCORE")
    private Integer selfScore;

    @Column(name = "SELF_SCORE_LEVEL", length = 20)
    private String selfScoreLevel;

    @Column(name = "FIRST_APPRAISER_FEEDBACK", length = 1000)
    private String firstAppraiserFeedback;

    @Column(name = "FIRST_APPRAISER_CONTRIBUTION", length = 20, nullable = false)
    private String firstAppraiserContribution = "L";

    @Column(name = "FIRST_APPRAISER_QUALITATIVE", length = 1000)
    private String firstAppraiserQualitative;

    @Column(name = "FIRST_APPRAISER_SCORE")
    private Integer firstAppraiserScore;

    @Column(name = "FIRST_APPRAISER_SCORE_LEVEL", length = 20)
    private String firstAppraiserScoreLevel;

    @Column(name = "SECOND_APPRAISER_FEEDBACK", length = 1000)
    private String secondAppraiserFeedback;

    @Column(name = "SECOND_APPRAISER_CONTRIBUTION", length = 20, nullable = false)
    private String secondAppraiserContribution = "L";

    @Column(name = "SECOND_APPRAISER_QUALITATIVE", length = 1000)
    private String secondAppraiserQualitative;

    @Column(name = "SECOND_APPRAISER_SCORE")
    private Integer secondAppraiserScore;

    @Column(name = "SECOND_APPRAISER_SCORE_LEVEL", length = 20)
    private String secondAppraiserScoreLevel;
}
