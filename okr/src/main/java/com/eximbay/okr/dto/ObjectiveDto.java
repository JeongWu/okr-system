package com.eximbay.okr.dto;

import com.eximbay.okr.entity.Company;
import com.eximbay.okr.entity.Division;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.Team;
import lombok.Data;

import java.time.Instant;

@Data
public class ObjectiveDto {

    private Integer objectiveSeq;
    private int year;
    private int quarter;
    private String beginDate;
    private String endDate;
    private String objectiveLevel;
    private CompanyDto company;
    private DivisionDto division;
    private TeamDto team;
    private MemberDto member;
    private String objective;
    private int priority;
    private int progress;
    private Instant lastUpdateDate;
    private String closeFlag;
    private String closeJustification;
    private Instant closeDate;
}
