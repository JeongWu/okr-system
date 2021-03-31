package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.division.AddDivisionMemberFormDto;
import com.eximbay.okr.dto.division.DivisionDto;
import com.eximbay.okr.dto.division.RemoveDivisionMemberFormDto;
import com.eximbay.okr.dto.divisionmember.DivisionMemberDto;
import com.eximbay.okr.dto.divisionmember.DivisionMemberWithTimeDto;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.entity.DivisionMember;
import com.eximbay.okr.entity.id.DivisionMemberId;

import java.util.List;

public interface IDivisionMemberService extends IService<DivisionMemberDto, DivisionMemberId> {
    void removeDivisionMember(RemoveDivisionMemberFormDto dto);

    void addDivisionMember(AddDivisionMemberFormDto dto);

    List<MemberDto> findAvailableMemberToAddToDivision(DivisionDto divisionDto);

    List<MemberDto> findActiveMembersOfDivision(DivisionDto divisionDto);

    List<DivisionMemberWithTimeDto> findActiveMembersOfDivisionWithTime(DivisionDto divisionDto);

    List<DivisionMember> findActiveInFutureByMember(Integer memberSeq);
}
