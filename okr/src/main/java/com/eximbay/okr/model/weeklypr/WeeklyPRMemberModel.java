package com.eximbay.okr.model.weeklypr;

import java.util.List;

import com.eximbay.okr.constant.Subheader;

import lombok.Data;

@Data
public class WeeklyPRMemberModel {

    private String subheader = Subheader.WEEKLY_PRS_MEMBERS;
    private String mutedHeader;
    private List<Integer> years;
    private List<String> teamNames;
}
