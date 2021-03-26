package com.eximbay.okr.model.okrSchedule;

import java.util.List;

import com.eximbay.okr.dto.DivisionDto;

import lombok.Data;

@Data
public class EditOkrScheduleModel {
    private String subheader;
//    private String mutedheader;
    private QuarterlyScheduleUpdateModel quarterlyModel;
    private MonthlyScheduleUpdateModel monthlyModel;
    private WeeklyScheduleUpdateModel weeklyModel;
} 
