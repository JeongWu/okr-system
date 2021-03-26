package com.eximbay.okr.model.okrSchedule;

import lombok.Data;
import com.eximbay.okr.enumeration.ScheduleType;


@Data
public class QuarterlyScheduleUpdateModel {
    private Integer scheduleSeq;
    private ScheduleType scheduleType = ScheduleType.QUARTERLY;
    private Integer beginDay;
    private Integer endDay;
    private Integer closeAfterDays;
    private String remindBeforeDays;
    private String[] remindBeforeDaysList;
    private String remindTime;
}
