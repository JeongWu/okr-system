package com.eximbay.okr.dto;

import com.eximbay.okr.enumeration.TeamType;

import lombok.Data;

@Data
public class TeamHistoryDto {

    private Integer historySeq;
    private TeamDto team;
    private DivisionDto division;
    private String name;
    private String localName;
    private TeamType teamType;
    private String introduction;
    private String image;
    private String useFlag;
    private String justification;
}
