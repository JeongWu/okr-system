package com.eximbay.okr.dto.objectivehistory;

import com.eximbay.okr.dto.company.CompanyDto;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class ObjectiveHistoryDto extends AbstractAuditableDto {

    private Integer historySeq;
    private ObjectiveDto objectiveObject;
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
    private Integer proportion;
    private Integer progress;
    private Instant latestUpdateDt;
    private String closeFlag;
    private String closeJustification;
    private Instant closeDate;
    private String justification;
}
