package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "team_member")
@Entity
public class TeamMember extends AbstractAuditable {

    @EmbeddedId
    private TeamMemberId teamMemberId;

    @Column(name = "TEAM_LEAD_FLAG", length = 1, nullable = false)
    private String teamLeadFlag = FlagOption.N;

    @Column(name = "TEAM_MANAGER_FLAG", length = 1, nullable = false)
    private String teamManagerFlag = FlagOption.N;

    @Column(name = "EDIT_TEAM_OKR_FLAG", length = 1, nullable = false)
    private String editTeamOkrFlag = FlagOption.N;

    @Column(name = "APPLY_END_DATE", length = 10, nullable = false)
    private String applyEndDate;

    @Column(name = "JUSTIFICATION")
    private String justification;
}
