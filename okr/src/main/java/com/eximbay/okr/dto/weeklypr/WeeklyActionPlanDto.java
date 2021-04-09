package com.eximbay.okr.dto.weeklypr;

import java.time.Instant;

import com.eximbay.okr.entity.WeeklyPrCard;
import com.eximbay.okr.listener.AbstractAuditableDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WeeklyActionPlanDto extends AbstractAuditableDto {
    private Integer weeklyPRSeq;
    private WeeklyPrCard weeklyPrCard;
    private String review;
    protected Instant reviewDate;
}
