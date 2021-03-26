package com.eximbay.okr.model;

import com.eximbay.okr.entity.Division;

import lombok.Data;

@Data
public class EditTeamModel {
    private String subheader;
    private String mutedHeader;
    private TeamUpdateFormModel model;
} 
