package com.eximbay.okr.dto.okeschedule;

import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OkrScheduleDto extends AbstractAuditableDto {
    private Integer scheduleSeq;
    private String scheduleType;
    private Integer beginDay;
    private Integer endDay;
    private Integer closeAfterDays;
    private String remindBeforeDays;
    private String remindTime;
}
