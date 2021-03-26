package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.*;
import com.eximbay.okr.dto.divisionmember.DivisionMemberWithTimeDto;
import com.eximbay.okr.entity.*;

import java.util.List;

public interface IDivisionMemberService extends ISerivce<DivisionMemberDto, DivisionMemberId> {
    void removeDivisionMember(RemoveDivisionMemberFormDto dto);
    void addDivisionMember(AddDivisionMemberFormDto dto);
    List<MemberDto> findAvailableMemberToAddToDivision(DivisionDto divisionDto);
    List<MemberDto> findActiveMembersOfDivision(DivisionDto divisionDto);
    List<DivisionMemberWithTimeDto> findActiveMembersOfDivisionWithTime(DivisionDto divisionDto);
    List<DivisionMember> findActiveInFutureByMember(Integer memberSeq);
}
