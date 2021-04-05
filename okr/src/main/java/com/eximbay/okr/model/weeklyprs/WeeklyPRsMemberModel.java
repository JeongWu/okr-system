package com.eximbay.okr.model.weeklyprs;

import com.eximbay.okr.constant.MutedHeader;
import com.eximbay.okr.constant.Subheader;

import lombok.Data;

@Data
public class WeeklyPRsMemberModel {

    private String subheader = Subheader.WEEKLY_PRS_MEMBERS;
    private String mutedHeader;
    // private List<MemberDto> members = new ArrayList<>();
}
