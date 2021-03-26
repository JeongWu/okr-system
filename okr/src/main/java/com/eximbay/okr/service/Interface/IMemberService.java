package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.model.profile.EditProfileModel;
import com.eximbay.okr.model.profile.ProfileUpdateModel;

import java.util.List;
import java.util.Optional;

public interface IMemberService extends ISerivce<MemberDto, Integer>{
    Optional<MemberDto> findByEmail(String email);
    List<MemberDto> findActiveMembers();
    List<MemberDto> findActiveLeadOrDirectorMembers();
    Optional<MemberDto> getCurrentMember();

    EditProfileModel buildEditProfileModel(Integer id);
    // void updateProfileModel(ProfileUpdateModel profileUpdateModel); 
}
