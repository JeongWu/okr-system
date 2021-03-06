package com.eximbay.okr.dto.divisionmember;

import com.eximbay.okr.dto.division.DivisionDto;
import com.eximbay.okr.dto.member.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DivisionMemberWithTimeDto {

    DivisionDto division;
    MemberDto member;
    String applyBeginDate;
    String applyEndDate;
}
