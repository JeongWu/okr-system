package com.eximbay.okr.dto;

import com.eximbay.okr.entity.*;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamMemberDto extends AbstractAuditableDto {
    private TeamMemberId teamMemberId;
    private String teamLeadFlag;
    private String teamManagerFlag;
    private String editTeamOkrFlag;
    private String applyEndDate;
    private String justification;
}
