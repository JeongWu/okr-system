package com.eximbay.okr.dto.objective;

import com.eximbay.okr.listener.AbstractAuditableDto;
import com.eximbay.okr.dto.CompanyDto;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.TeamDto;
import lombok.Data;

import java.time.Instant;

@Data
public class ObjectiveDto extends AbstractAuditableDto {

    private Integer objectiveSeq;
    private Integer year;
    private Integer quarter;
    private String beginDate;
    private String endDate;
    private String objectiveType;
    private CompanyDto company;
    private TeamDto team;
    private MemberDto member;
    private String objective;
    private Integer priority;
    private Integer progress;
    private Instant lastUpdateDate;
    private String closeFlag;
    private String closeJustification;
    private Instant closeDate;
}
