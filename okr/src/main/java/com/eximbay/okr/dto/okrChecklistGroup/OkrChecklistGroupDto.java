package com.eximbay.okr.dto.okrChecklistGroup;

import java.time.Instant;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.entity.TeamMemberId;
import com.eximbay.okr.listener.AbstractAuditableDto;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
public class OkrChecklistGroupDto{

    private Integer groupSeq;
    private ObjectiveDto objective;
    private String sourceObjective;
    private Integer objectiveSeq;
    private Integer objectiveScore;
    private Integer keyResult1Score;
    private Integer keyResult2Score;
    private Integer keyResult3Score;
    private Integer keyResult4Score;
    private Integer keyResult5Score;
    protected String createdBy = "system";
    protected Instant createdDate;
    
    
}
