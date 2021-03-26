package com.eximbay.okr.model;

import java.util.List;

import com.eximbay.okr.dto.DivisionDto;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.enumeration.TeamType;

import lombok.Data;

@Data
public class TeamListTableModel {

    private Integer teamSeq;
    private DivisionDto division;
    private String divisionName;
    private String name;
    private String localName;
    private TeamType teamType;
    private String image;
    private String useFlag;
    private MemberDto leaderOrManager;
    private List<MemberDto> members;
}
    
