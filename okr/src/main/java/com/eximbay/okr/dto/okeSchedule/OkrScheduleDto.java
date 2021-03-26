package com.eximbay.okr.dto.okeSchedule;

import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;

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
