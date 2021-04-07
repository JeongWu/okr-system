package com.eximbay.okr.dto.weekly;

import java.time.Instant;

import com.eximbay.okr.entity.WeeklyPRCard;
import com.eximbay.okr.listener.AbstractAuditableDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WeeklyActionPlanDto extends AbstractAuditableDto {
    private Integer weeklyPRSeq;
    private WeeklyPRCard weeklyPRCard;
    private String review;
    protected Instant reviewDate;
}
