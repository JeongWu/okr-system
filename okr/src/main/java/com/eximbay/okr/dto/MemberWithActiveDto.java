package com.eximbay.okr.dto;

import java.util.List;

import com.eximbay.okr.dto.objective.ObjectiveDto;

import lombok.Data;

@Data
public class MemberWithActiveDto {
    private Integer memberSeq;
    private String name;
    private String localName;
    private String position;
    private String introduction;
    private String image;
    private String useFlag;
    private List<ObjectiveDto> objectives;
    private List<TeamDto> teams;
    private Integer feedbacks;
    private Integer keyResults;

}
