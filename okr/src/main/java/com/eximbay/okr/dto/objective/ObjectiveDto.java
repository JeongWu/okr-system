package com.eximbay.okr.dto.objective;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.company.CompanyDto;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import com.eximbay.okr.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
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
    private Integer proportion;
    private Integer progress;
    private Instant latestUpdateDt;
    private String closeFlag;
    private String closeJustification;
    private Instant closeDate;

    @JsonIgnore
    private List<KeyResultDto> keyResults;

    // fields not in entity objective
    private String justification;
    private Integer sumProportionOfOtherObjectives;

    public String getDisplayBeginDate() {
        return this.beginDate == null ? null : StringUtils.addDashToDBDate(this.beginDate);
    }

    public String getDisplayEndDate() {
        return this.endDate == null ? null : StringUtils.addDashToDBDate(this.endDate);
    }

    public boolean isClosed() {
        return FlagOption.isYes(this.closeFlag);
    }
}
