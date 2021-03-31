package com.eximbay.okr.model.member;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.model.objective.ObjectiveViewOkrModel;
import lombok.Data;

import java.util.List;

@Data
public class MemberViewOkrModel {

    private String subheader = Subheader.VIEW_OKR;
    private String mutedHeader = "Member name";
    private List<ObjectiveViewOkrModel> objectives;
    private boolean editable;
    private String quarter;
    private MemberDto member;
}
