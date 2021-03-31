package com.eximbay.okr.controller;

import com.eximbay.okr.constant.ErrorMessages;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.enumeration.EntityType;
import com.eximbay.okr.enumeration.FileContentType;
import com.eximbay.okr.enumeration.FileType;
import com.eximbay.okr.exception.RestUserException;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.profile.EditProfileModel;
import com.eximbay.okr.model.profile.ProfileUpdateModel;
import com.eximbay.okr.service.FileUploadService;
import com.eximbay.okr.service.Interface.IMemberService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final FileUploadService fileUploadService;
    private final IMemberService memberService;

    @GetMapping("/edit")
    public String editProfile(Model model) {

        Optional<MemberDto> currentMember = memberService.getCurrentMember();
        if (currentMember.isEmpty()) throw new UserException(ErrorMessages.loginRequired);

        EditProfileModel viewModel = memberService.buildEditProfileModel(currentMember.get().getMemberSeq());

        model.addAttribute("model", viewModel);
        model.addAttribute("dataModel", viewModel.getModel());
        return "pages/profile/edit-profile";
    }

    @PostMapping("/save")
    public String updateProfile(@Validated ProfileUpdateModel profileUpdateModel, BindingResult error) {

        if (error.hasErrors())
            return "redirect:/profile/edit/profile/" + profileUpdateModel.getMemberSeq();

        Optional<MemberDto> member = memberService.findById(profileUpdateModel.getMemberSeq());

        if (member.isEmpty())
            throw new UserException(
                    new NotFoundException("Not found Object with Id = " + profileUpdateModel.getMemberSeq()));

        if (profileUpdateModel.getImageFile() != null && !profileUpdateModel.getImageFile().isEmpty()) {
            String imageSrc;
            try {
                imageSrc = fileUploadService.store(FileType.IMAGE, FileContentType.AVATAR, EntityType.MEMBER,
                        profileUpdateModel.getImageFile());
            } catch (UserException e) {
                String message = Optional.ofNullable(e.getCause()).orElse(e).getMessage();
                throw new RestUserException(message);
            }
            member.get().setImage(imageSrc);
        }

        member.get().setIntroduction(profileUpdateModel.getIntroduction());

        memberService.save(member.get());
        profileUpdateModel.setImageFile(null);
        return "redirect:/";
    }

}
