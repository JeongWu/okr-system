package com.eximbay.okr.dto.teamhistory;

import com.eximbay.okr.dto.division.DivisionDto;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.enumeration.TeamType;
import com.eximbay.okr.listener.AbstractAuditableDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamHistoryDto extends AbstractAuditableDto {
    private Integer historySeq;
    private TeamDto team;
    private DivisionDto division;
    private String name;
    private String localName;
    private TeamType teamType;
    private String introduction;
    private String image;
    private String useFlag;
    private String justification;
}
