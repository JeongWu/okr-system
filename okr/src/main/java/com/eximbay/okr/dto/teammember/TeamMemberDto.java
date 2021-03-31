package com.eximbay.okr.dto.teammember;

import com.eximbay.okr.entity.id.TeamMemberId;
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
