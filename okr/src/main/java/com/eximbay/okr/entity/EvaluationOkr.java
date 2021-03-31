package com.eximbay.okr.entity;

import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "evaluation_okr")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EvaluationOkr extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OKR_SEQ", length = 11)
    private Integer okrSeq;

    @Column(name = "YEAR", length = 4, nullable = false)
    private Integer year;

    @Column(name = "QUARTER", length = 11, nullable = false)
    private Integer quarter;

    @Column(name = "QUARTER_END_DATE", length = 8, nullable = false)
    private String quarterEndDate;

    @Column(name = "OBJECTIVE_TYPE", length = 10, nullable = false)
    private String objectiveType;

    @Column(name = "APPRAISEE_SEQ", length = 11)
    private Integer appraiseeSeq;

    @Column(name = "SELF_REVIEW", length = 1000)
    private String selfReview;

    @Column(name = "SELF_END_DT")
    private Instant selfEndDate;

    @ManyToOne
    @JoinColumn(name = "FIRST_APPRAISER_SEQ")
    private Member firstAppraiser;

    @Column(name = "FIRST_APPRAISER_REVIEW", length = 1000)
    private String firstAppraiserReview;

    @Column(name = "FIRST_APPRAISER_END_DT")
    private Instant firstAppraiserEndDate;

    @ManyToOne
    @JoinColumn(name = "SECOND_APPRAISER_SEQ")
    private Member secondAppraiser;

    @Column(name = "SECOND_APPRAISER_REVIEW", length = 1000)
    private String secondAppraiserReview;

    @Column(name = "SECOND_APPRAISER_END_DT")
    private Instant secondAppraiserEndDate;

    @Column(name = "SUMMARY_SCORE", precision = 5, scale = 2, nullable = false)
    private BigDecimal summaryScore = BigDecimal.ZERO;

    @Column(name = "SUMMARY_SCORE_LEVEL", length = 20)
    private String summaryScoreLevel;

    @Column(name = "OKR_ESTABLISHMENT_LEVEL", length = 20)
    private String okrEstablishmentLevel;

    @Column(name = "OKR_MANAGEMENT_LEVEL", length = 20)
    private String okrManagementLevel;

    @Column(name = "HR_REVIEW", length = 1000)
    private String hrReview;
}
