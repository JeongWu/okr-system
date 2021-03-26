package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.MemberDto;

import java.util.List;
import java.util.Optional;

public interface IMemberService extends ISerivce<MemberDto, Integer>{
    Optional<MemberDto> findByEmail(String email);
    List<MemberDto> findActiveMembers();
    List<MemberDto> findActiveLeadOrDirectorMembers();
}
