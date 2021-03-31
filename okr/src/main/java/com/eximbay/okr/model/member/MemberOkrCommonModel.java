package com.eximbay.okr.model.member;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.codelist.CodeListDto;
import lombok.Data;

import java.util.List;

@Data
public class MemberOkrCommonModel {
    private String subheader = Subheader.ADD_OKR;
    private String mutedHeader = "Member Name";
    private List<CodeListDto> objectiveLevels;
    private List<CodeListDto> taskTypes;
    private List<CodeListDto> taskMetrics;
    private List<CodeListDto> taskIndicators;
    private int memberId;
}
