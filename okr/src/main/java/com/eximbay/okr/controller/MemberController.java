package com.eximbay.okr.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.MemberHistoryDto;
import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.dto.TeamMemberDto;
import com.eximbay.okr.enumeration.EntityType;
import com.eximbay.okr.enumeration.FileContentType;
import com.eximbay.okr.enumeration.FileType;
import com.eximbay.okr.exception.RestUserException;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.MemberModel;
import com.eximbay.okr.model.PageModel;
import com.eximbay.okr.model.profile.EditProfileModel;
import com.eximbay.okr.model.profile.ProfileUpdateModel;
import com.eximbay.okr.service.FileUploadService;
import com.eximbay.okr.service.MemberHistoryServiceImpl;
import com.eximbay.okr.service.MemberServiceImpl;
import com.eximbay.okr.service.TeamMemberServiceImpl;
import com.eximbay.okr.service.TeamServiceImpl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;

@Controller
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberServiceImpl memberService;
    private final MemberHistoryServiceImpl memberHistoryService;
    private final TeamMemberServiceImpl teamMemberService;
    private final FileUploadService fileUploadService;
    private final TeamServiceImpl teamService;
    private final MapperFacade mapper;

    @GetMapping
    public String memberList(Model model) {

        List<MemberDto> members = memberService.findAll();
        List<TeamDto> teams = teamService.findAll();
        List<String> teamName = teams.stream().map(t -> t.getName()).collect(Collectors.toList());
        model.addAttribute("teamName", teamName);

        PageModel pageModel = new PageModel();
        long totalCount = members.size();
        pageModel.setSubheader(Subheader.MEMBER);
        pageModel.setMutedHeader(totalCount + " total");
        model.addAttribute("model", pageModel);

        return "pages/members/member_list";
    }

    @GetMapping("/add")
    public String addMember(Model model) {
        model.addAttribute("subheader", Subheader.ADD_MEMBER);
        return "pages/members/member_add";
    }

    @PostMapping("/add")
    public String memberSave(MemberDto dto, @RequestParam("picture") MultipartFile files,
            @RequestParam("action") String action) {
        if (!files.isEmpty()) {
            // fileUploadService.store(FileType.IMAGE, FileContentType.AVATAR,
            // EntityType.MEMBER, files);
        }
        dto.createMemberDto(dto.getJoiningDate(), dto.getRetirementDate(), dto.getEditCompanyOkrFlag(),
                dto.getUseFlag(), dto.getAdminFlag(), dto.getEmail(), files);
        memberService.save(dto);
        if (action.equals("new")) {
            return "redirect:/members/add";
        }
        return "redirect:/members";
    }

    @GetMapping("/edit/{memberSeq}")
    public String editdetails(Model model, @PathVariable("memberSeq") int id) {
        MemberDto dto = memberService.findById(id).orElseThrow(() -> new NullPointerException("Null"));
        MemberModel memberModel = new MemberModel(Subheader.EDIT_MEMBER, dto.getName());
        model.addAttribute("model", memberModel);
        model.addAttribute("member", dto);
        model.addAttribute("team", teamMemberService.findSearchBelong(dto));
        return "pages/members/member_edit";
    }

    @PostMapping(value = "/save")
    public String memberUpdate(@Validated MemberDto req, BindingResult error,
            @RequestParam("picture") MultipartFile files) {
        if (error.hasErrors())
            return "redirect:/members/edit/" + req.getMemberSeq();
        return "redirect:/members";
    }

    @PostMapping("/edit/{memberSeq}")
    public String memberUpdate(MemberDto req, @RequestParam("picture") MultipartFile files,
            @PathVariable("memberSeq") int id) {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyMMdd"));
        MemberDto dto = memberService.findById(id).orElseThrow(() -> new NullPointerException("Null"));

        dto.setName(req.getName());
        dto.setLocalName(req.getLocalName());
        dto.setEmail(req.getEmail());
        dto.setContactPhone(req.getContactPhone());
        dto.setRetirementDate(req.getRetirementDate());
        dto.setJoiningDate(req.getJoiningDate());
        dto.setCareer(req.getCareer());
        dto.setLevel(req.getLevel());
        dto.setIntroduction(req.getIntroduction());
        dto.setJustification(req.getJustification());
        dto.setPosition(req.getPosition());
        dto.setEditCompanyOkrFlag(req.getEditCompanyOkrFlag());

        if (req.getUseFlag() == null) {
            List<TeamMemberDto> teams = teamMemberService.findSearchBelong(dto);
            for (TeamMemberDto item : teams) {
                item.setApplyEndDate(today);
                item.setJustification("Inactive");
                teamMemberService.save(item);
            }
        }

        dto.createMemberDto(req.getJoiningDate(), req.getRetirementDate(), req.getEditCompanyOkrFlag(),
                req.getUseFlag(), req.getAdminFlag(), req.getEmail(), files);

        MemberHistoryDto history = new MemberHistoryDto();
        history.setName(dto.getName());
        history.setMember(dto);
        history.setLocalName(dto.getLocalName());
        history.setEmail(dto.getEmail());
        history.setContactPhone(dto.getContactPhone());
        history.setJoiningDate(dto.getJoiningDate());
        history.setRetirementDate(dto.getRetirementDate());
        history.setCareer(dto.getCareer());
        history.setLevel(dto.getLevel());
        history.setIntroduction(dto.getIntroduction());
        history.setJustification(dto.getJustification());
        history.setPosition(dto.getPosition());

        history.createMemberHistoryDto(req.getJoiningDate(), req.getRetirementDate(), req.getEditCompanyOkrFlag(),
                req.getUseFlag(), req.getAdminFlag(), req.getEmail(), files);

        memberService.save(dto);
        memberHistoryService.save(history);
        if (!files.isEmpty()) {
            // fileUploadService.store(FileType.IMAGE, FileContentType.AVATAR,
            // EntityType.MEMBER, files);
        }
        return "redirect:/members";
    }

    @GetMapping("/belong/{memberSeq}")
    public String viewBelong(Model model, @PathVariable("memberSeq") Integer memberSeq) {
        MemberDto dto = memberService.findById(memberSeq).orElseThrow(() -> new NullPointerException("Null"));
        MemberModel belongModel = new MemberModel(Subheader.MEMBER_HISTORY, dto.getName());
        model.addAttribute("member", dto);
        model.addAttribute("model", belongModel);
        model.addAttribute("memberSeq", memberSeq);
        return "pages/members/member_belong";
    }


}