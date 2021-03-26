package com.eximbay.okr.model;

import com.eximbay.okr.constant.Subheader;
import lombok.Data;

@Data
public class MemberHistoryModel {
    private String subheader = Subheader.MEMBER_HISTORY;
    private String mutedHeader;
    private String name;
}

