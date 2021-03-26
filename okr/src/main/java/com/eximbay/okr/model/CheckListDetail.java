package com.eximbay.okr.model;

import com.eximbay.okr.constant.Subheader;

import lombok.Data;

@Data
public class CheckListDetail {
    private String subheader = Subheader.OKR_CHECKLIST_GROUP;
    private String mutedHeader;
}
