package com.eximbay.okr.model;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.division.DivisionDto;
import com.eximbay.okr.dto.member.MemberDto;
import lombok.Data;

import java.util.List;

@Data
public class DivisionChangeMembersModel {
    private String subheader = Subheader.DIVISION_CHANGE_MEMBERS;
    private String mutedHeader;
    private DivisionDto division;
    List<MemberDto> availableMembers;
}
