package com.eximbay.okr.dto.objective;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.CompanyDto;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import com.eximbay.okr.utils.StringUtils;
import lombok.Data;

import java.time.Instant;
import java.util.List;

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
    private Integer proportion = 0;
    private Integer progress = 0;
    private Instant lastUpdateDate;
    private String closeFlag;
    private String closeJustification;
    private Instant closeDate;

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
