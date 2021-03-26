package com.eximbay.okr.dto;

import com.eximbay.okr.enumeration.TeamType;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamDto extends AbstractAuditableDto {

    private Integer teamSeq;
    private DivisionDto division;
    private String name;
    private String localName;
    private TeamType teamType;
    private String introduction;
    private String image;
    private String useFlag;
    private MemberDto leaderOrManager;
}
