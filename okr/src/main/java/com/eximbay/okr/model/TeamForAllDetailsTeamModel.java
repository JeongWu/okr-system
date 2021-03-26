package com.eximbay.okr.model;

import com.eximbay.okr.dto.DivisionDto;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.enumeration.TeamType;
import lombok.Data;

import java.util.List;

@Data
public class TeamForAllDetailsTeamModel {
    private Integer teamSeq;
    private DivisionDto division;
    private String name;
    private String localName;
    private TeamType teamType;
    private String introduction;
    private String image;
    private String useFlag;
    private List<ObjectiveDto> objectives;

    private MemberDto leaderOrManager;
    private boolean isEditable = true;
    private List<MemberDto> members;

}
