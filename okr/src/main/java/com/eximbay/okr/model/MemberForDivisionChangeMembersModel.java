package com.eximbay.okr.model;

import com.eximbay.okr.dto.team.TeamDto;
import lombok.Data;

import java.util.List;

@Data
public class MemberForDivisionChangeMembersModel {

    private Integer memberSeq;
    private String name;
    private String localName;
    private String image;
    private String position;
    private String useFlag;
    private List<TeamDto> teams;
    private String applyBeginDate;
}
