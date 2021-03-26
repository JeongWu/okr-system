package com.eximbay.okr.dto;

import com.eximbay.okr.entity.Division;
import com.eximbay.okr.enumeration.TeamType;
import lombok.Data;

@Data
public class TeamDto {

    private Integer teamSeq;
    private DivisionDto division;
    private String name;
    private String localName;
    private TeamType teamType;
    private String introduction;
    private String image;
    private String useFlag;
    private MemberDto leaderOrManager;
}
