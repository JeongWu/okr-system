package com.eximbay.okr.dto.weeklypr;

import com.eximbay.okr.listener.AbstractAuditableDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WeeklyPrCardDto extends AbstractAuditableDto {
    private Integer weeklySeq;
    private Integer year;
    private Integer week;
    private String beginDate;
    private String endDate;
    private String weekEndDate;
    // private MemberDto member;
    private String challenge;

    private Integer sumActionPlan;
    private Integer sumReview;
}
