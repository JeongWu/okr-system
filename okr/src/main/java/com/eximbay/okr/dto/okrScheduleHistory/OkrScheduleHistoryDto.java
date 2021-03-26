package com.eximbay.okr.dto.okrScheduleHistory;

import com.eximbay.okr.dto.okeSchedule.OkrScheduleDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OkrScheduleHistoryDto extends AbstractAuditableDto {
    private Integer historySeq;
    private OkrScheduleDto okrSchedule;
    private String scheduleType;
    private Integer beginDay;
    private Integer endDay;
    private Integer closeAfterDays;
    private String remindBeforeDays;
    private String remindTime;
    private String justification;
}
