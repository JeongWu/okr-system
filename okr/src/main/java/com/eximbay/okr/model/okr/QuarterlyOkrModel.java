package com.eximbay.okr.model.okr;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.model.TeamForWireframeModel;
import lombok.Data;

import java.util.List;

@Data
public class QuarterlyOkrModel {

    private String subheader = Subheader.QUARTERLY_OKRS;
    private String mutedHeader;
    private List<TeamForWireframeModel> teams;
    private String type = "default";
    private String currentQuarter;
    private Integer seq;
    private List<MemberDto> members;
}
