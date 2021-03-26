package com.eximbay.okr.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ObjectiveDto {

    private Integer objectiveSeq;
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
}
