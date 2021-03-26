package com.eximbay.okr.model.okrChecklistGroup;

import com.eximbay.okr.constant.Subheader;

import lombok.Data;

@Data
public class OkrChecklistGroupsModel {
    private String subheader = Subheader.OKR_CHECKLIST_GROUP;
    private String mutedHeader;
    
}

