package com.eximbay.okr.model;

import lombok.Data;

@Data
public class EditForViewAllTeamsModel {
    private String subheader;
    private String mutedHeader;
   
    private AllTeamUpdateModel model;
} 
