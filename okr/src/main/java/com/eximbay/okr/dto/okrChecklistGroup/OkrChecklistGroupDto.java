package com.eximbay.okr.dto.okrchecklistgroup;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import lombok.Data;

import java.time.Instant;


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
    protected String createdBy;
    protected Instant createdDate;

}
