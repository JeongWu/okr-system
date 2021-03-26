package com.eximbay.okr.model;

import java.util.List;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.Division;
import com.eximbay.okr.enumeration.TeamType;

import lombok.Data;

@Data
public class TeamHistoryForTeamHistoriesModel {

    private Integer historySeq;
    private String name;
    private Division division;
    private TeamType teamType;
    private String useFlag = FlagOption.Y;
    
    private String modUserId;
    private String modDt;
    
    private String justification;
  
}