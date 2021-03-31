package com.eximbay.okr.model;

import java.util.List;

import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;

import lombok.Data;

@Data
public class MemberForAllDetailsMemberModel {

    private Integer memberSeq;
    private String name;
    private String localName;
    private String position;
    private String introduction;
    private String image;
    private String useFlag;
    private List<ObjectiveDto> objectives;
    private List<TeamDto> teams;
    private Integer keyResults = 0;
    private Integer feedbacks = 0;
}
