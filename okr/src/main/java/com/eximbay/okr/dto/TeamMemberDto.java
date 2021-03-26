package com.eximbay.okr.dto;

import com.eximbay.okr.entity.*;
import lombok.Data;

@Data
public class TeamMemberDto {
    private TeamMemberId teamMemberId;
    private String teamLeadFlag;
    private String teamManagerFlag;
    private String applyEndDate;
    private String justification;
}
