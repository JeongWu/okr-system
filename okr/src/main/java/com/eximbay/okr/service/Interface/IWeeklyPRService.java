package com.eximbay.okr.service.Interface;

import java.util.List;

import com.eximbay.okr.dto.weekly.MemberDatatablesInput;
import com.eximbay.okr.dto.weekly.MemberswithWeeklyPRCardDto;
import com.eximbay.okr.dto.weekly.TestData;
import com.eximbay.okr.dto.weekly.TestDto;
import com.eximbay.okr.dto.weekly.WeeklyPRCardwithMembersDto;
import com.eximbay.okr.entity.WeeklyPRCard;
import com.eximbay.okr.model.weeklypr.WeeklyPRMemberModel;
import com.eximbay.okr.model.weeklypr.YearnAndWeekModel;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface IWeeklyPRService {
    WeeklyPRMemberModel buildViewAllMembersModel();
    DataTablesOutput<WeeklyPRCard> getDataForDatatables(MemberDatatablesInput input);
    List<WeeklyPRCardwithMembersDto> getMembersDatatables();
    List<MemberswithWeeklyPRCardDto> getDatas();
    List<TestDto> test();
    YearnAndWeekModel getWeekByYear(Integer year);
    List<TestData> testData();

}
