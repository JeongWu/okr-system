package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.model.AllDetailsMemberModel;
import com.eximbay.okr.model.member.MemberViewOkrModel;
import com.eximbay.okr.model.profile.EditProfileModel;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IMemberService extends IService<MemberDto, Integer> {
    Optional<MemberDto> findByEmail(String email);

    List<MemberDto> findActiveMembers();

    List<MemberDto> findActiveLeadOrDirectorMembers();

    Optional<MemberDto> getCurrentMember();

    MemberViewOkrModel buildMemberViewOkrModel(Integer memberSeq, String quarter);

    AllDetailsMemberModel buildAllDetailsMemberModel(Pageable pageable);

    EditProfileModel buildEditProfileModel(Integer id);
}
