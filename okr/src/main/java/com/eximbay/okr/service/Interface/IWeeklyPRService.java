package com.eximbay.okr.service.Interface;

import java.util.List;

import com.eximbay.okr.dto.weeklypr.MembersWithWeeklyPrCardDto;
import com.eximbay.okr.dto.weeklypr.WeeklyPrCardDto;
import com.eximbay.okr.entity.WeeklyPrCard;
import com.eximbay.okr.model.weeklypr.WeeklyPrMemberModel;
import com.eximbay.okr.model.weeklypr.YearnAndWeekModel;

public interface IWeeklyPrService {
    WeeklyPrMemberModel buildViewAllMembersModel();
    YearnAndWeekModel getWeekByYear(Integer year);
    List<MembersWithWeeklyPrCardDto> getDataForDatatables();
    WeeklyPrCardDto sumReviewAndActionPlan(WeeklyPrCard weeklyPrCard);
}
