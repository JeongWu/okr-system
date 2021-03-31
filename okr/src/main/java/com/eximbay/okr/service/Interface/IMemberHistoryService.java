package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.memberhistory.MemberHistoryDto;

import java.util.List;

public interface IMemberHistoryService extends IService<MemberHistoryDto, Integer> {
    List<MemberHistoryDto> findByName(String name);

    List<MemberHistoryDto> findByMember(MemberDto member);
}
