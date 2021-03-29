package com.eximbay.okr.model.team;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.CodeListDto;
import lombok.Data;

import java.util.List;

@Data
public abstract class TeamOkrCommonModel {
    private String subheader = Subheader.ADD_OKR;
    private String mutedHeader = "Team Local Name";
    private List<CodeListDto> objectiveLevels;
    private List<CodeListDto> taskTypes;
    private List<CodeListDto> taskMetrics;
    private List<CodeListDto> taskIndicators;
    private int teamId;
}
