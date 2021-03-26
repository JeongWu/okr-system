package com.eximbay.okr.service.Interface;

import java.util.List;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.MemberHistoryDto;

public interface IMemberHistoryService extends ISerivce<MemberHistoryDto, Integer> {
	List<MemberHistoryDto> findByName(String name);
	List<MemberHistoryDto> findByMember(MemberDto member);
}
