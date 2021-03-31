package com.eximbay.okr.dto.evaluationokr;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class EvaluationOkrDto extends AbstractAuditableDto {

    private Integer okrSeq;
    private Integer year;
    private Integer quarter;
    private String quarterEndDate;
    private String objectiveType;
    private Integer appraiseeSeq;
    private String selfReview;
    private Instant selfEndDate;
    private MemberDto firstAppraiser;
    private String firstAppraiserReview;
    private Instant firstAppraiserEndDate;
    private MemberDto secondAppraiser;
    private String secondAppraiserReview;
    private Instant secondAppraiserEndDate;
    private BigDecimal summaryScore;
    private String summaryScoreLevel;
    private String okrEstablishmentLevel;
    private String okrManagementLevel;
    private String hrReview;
}
