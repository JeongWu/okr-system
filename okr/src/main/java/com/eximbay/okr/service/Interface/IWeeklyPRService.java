package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.weeklypr.MemberDatatablesInput;
import com.eximbay.okr.entity.WeeklyPRCard;
import com.eximbay.okr.model.weeklypr.WeeklyPRMemberModel;
import com.eximbay.okr.model.weeklypr.YearnAndWeekModel;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface IWeeklyPRService {
    WeeklyPRMemberModel buildViewAllMembersModel();
    DataTablesOutput<WeeklyPRCard> getDataForDatatables(MemberDatatablesInput input);
    YearnAndWeekModel getWeekByYear(Integer year);
}
