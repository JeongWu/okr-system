package com.eximbay.okr.model.okrSchedule;

import lombok.Data;

@Data
public class EditOkrScheduleModel {
    private String subheader;
    private String mutedHeader;
    private QuarterlyScheduleUpdateModel quarterlyModel;
    private MonthlyScheduleUpdateModel monthlyModel;
    private WeeklyScheduleUpdateModel weeklyModel;
}
