package com.eximbay.okr.dto.objectiveHistory;

import com.eximbay.okr.dto.CompanyDto;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.ObjectiveDto;
import com.eximbay.okr.dto.TeamDto;
import lombok.Data;

import java.time.Instant;

@Data
public class ObjectiveHistoryDto {

    private Integer historySeq;
    private ObjectiveDto objectiveObject;
    private int year;
    private int quarter;
    private String beginDate;
    private String endDate;
    private String objectiveType;
    private CompanyDto company;
    private TeamDto team;
    private MemberDto member;
    private String objective;
    private int priority;
    private int progress;
    private Instant lastUpdateDate;
    private String closeFlag;
    private String closeJustification;
    private Instant closeDate;
    private Integer likes;
    private String justification;
}
