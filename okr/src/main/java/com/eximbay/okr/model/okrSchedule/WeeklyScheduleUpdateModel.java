package com.eximbay.okr.model.okrSchedule;

import com.eximbay.okr.enumeration.ScheduleType;

import lombok.Data;

@Data
public class WeeklyScheduleUpdateModel {
	private Integer scheduleSeq;
	private ScheduleType scheduleType = ScheduleType.WEEKLY;
	private Integer beginDay;
	private Integer endDay;
	private Integer closeAfterDays;
	private String remindBeforeDays;
	private String[] remindBeforeDaysList;
	private String remindTime;
}
