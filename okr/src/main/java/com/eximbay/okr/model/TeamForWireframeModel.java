package com.eximbay.okr.model;

import com.eximbay.okr.dto.division.DivisionDto;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.enumeration.TeamType;
import lombok.Data;

@Data
public class TeamForWireframeModel {
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
