package com.eximbay.okr.service;

import com.eximbay.okr.config.security.MyUserDetails;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.MemberPosition;
import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.*;
import com.eximbay.okr.entity.*;
import com.eximbay.okr.enumeration.EntityType;
import com.eximbay.okr.enumeration.FileContentType;
import com.eximbay.okr.enumeration.FileType;
import com.eximbay.okr.exception.RestUserException;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.profile.EditProfileModel;
import com.eximbay.okr.model.profile.ProfileUpdateModel;
import com.eximbay.okr.repository.*;
import com.eximbay.okr.service.Interface.*;
import com.eximbay.okr.service.specification.MemberQuery;
import lombok.*;
import ma.glasnost.orika.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class MemberServiceImpl implements IMemberService {
    private final MemberRepository memberRepository;
    private final MemberQuery memberQuery;
    private final MapperFacade mapper;
    // private final FileUploadService fileUploadService;

    @Override
    public List<MemberDto> findAll() {
        List<Member> members = memberRepository.findAll();
        return mapper.mapAsList(members, MemberDto.class);
    }

    @Override
    public Optional<MemberDto> findById(Integer id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.map(m-> mapper.map(m, MemberDto.class));
    }

    @Override
    public void remove(MemberDto memberDto) {
        Member member = mapper.map(memberDto, Member.class);
        memberRepository.delete(member);
    }

    @Override
    public MemberDto save(MemberDto memberDto) {
        Member member = mapper.map(memberDto, Member.class);
        member = memberRepository.save(member);
        return mapper.map(member, MemberDto.class);
    }

    @Override
    public Optional<MemberDto> findByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.map(m-> mapper.map(m, MemberDto.class));
    }

    @Override
    public List<MemberDto> findActiveMembers() {
        return mapper.mapAsList(memberRepository.findAll(memberQuery.findActiveMember()), MemberDto.class);
    }

    @Override
    public List<MemberDto> findActiveLeadOrDirectorMembers() {
        List<Member> members = memberRepository.findAll(
                memberQuery.findActiveMember()
                .and(memberQuery.findLeadOrDirectorMember())
        );
        return mapper.mapAsList(members, MemberDto.class);
    }

    @Override
    public Optional<MemberDto> getCurrentMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof MyUserDetails) return Optional.ofNullable(((MyUserDetails) principal).getMemberDto());
        return Optional.empty();
    }

    @Override
    public EditProfileModel buildEditProfileModel(Integer id) {
        EditProfileModel dataModel = new EditProfileModel();
        dataModel.setSubheader(Subheader.EDIT_PROFILE);
        Optional<Member> member = memberRepository.findById(id);
        Optional<ProfileUpdateModel> model = member.map(m -> mapper.map(m, ProfileUpdateModel.class));
        if (model.isEmpty())
            throw new UserException(new NotFoundException("Not found Object with Id = " + id));
        dataModel.setModel(model.get());
        return dataModel;
    }

    // @Override
    // public void updateProfileModel(ProfileUpdateModel profileUpdateModel) {
    //     Optional<Member> member = memberRepository.findById(profileUpdateModel.getMemberSeq());
    //     if (member.isEmpty())
    //         throw new UserException(
    //                 new NotFoundException("Not found Object with Id = " + profileUpdateModel.getMemberSeq()));

    //     if (profileUpdateModel.getImageFile() != null && !profileUpdateModel.getImageFile().isEmpty()) {
    //         String imageSrc;
    //         try {
    //             imageSrc = fileUploadService.store(FileType.IMAGE, FileContentType.AVATAR, EntityType.MEMBER,
    //                     profileUpdateModel.getImageFile());
    //         } catch (UserException e) {
    //             String message = Optional.ofNullable(e.getCause()).orElse(e).getMessage();
    //             throw new RestUserException(message);
    //         }
    //         member.get().setImage(imageSrc);
    //     }

    //     member.get().setIntroduction(profileUpdateModel.getIntroduction());

    //     memberRepository.save(member.get());
    //     profileUpdateModel.setImageFile(null);
    // }
    
}
