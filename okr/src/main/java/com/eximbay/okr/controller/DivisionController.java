package com.eximbay.okr.controller;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.division.AddDivisionMemberFormDto;
import com.eximbay.okr.dto.division.RemoveDivisionMemberFormDto;
import com.eximbay.okr.entity.Division;
import com.eximbay.okr.model.DivisionChangeMembersModel;
import com.eximbay.okr.model.DivisionUpdateFormModel;
import com.eximbay.okr.model.DivisionsModel;
import com.eximbay.okr.model.EditDivisionModel;
import com.eximbay.okr.model.division.DivisionAddModel;
import com.eximbay.okr.service.Interface.IDivisionMemberService;
import com.eximbay.okr.service.Interface.IDivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/divisions")
public class DivisionController {

    private final IDivisionService divisionService;
    private final IDivisionMemberService divisionMemberService;

    @GetMapping
    public String viewAllDivisions(Model model) {
        DivisionsModel divisionsModel = new DivisionsModel();
        model.addAttribute("model", divisionsModel);
        return "pages/divisions/divisions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model){
        EditDivisionModel viewModel = divisionService.buildEditDivisionModel(id);
        model.addAttribute("model", viewModel);
        model.addAttribute("dataModel",viewModel.getModel());
        return "pages/divisions/edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveDivision(@Validated DivisionUpdateFormModel updateFormModel, BindingResult error){
        if (error.hasErrors()) return "redirect:/divisions/edit/"+ updateFormModel.getDivisionSeq();
        divisionService.updateFormModel(updateFormModel);
        return "redirect:/divisions";
    }

    @GetMapping("/change-members/{id}")
    public String showChangeMembersList(@PathVariable Integer id, Model model){
        DivisionChangeMembersModel viewModel = divisionService.buildDivisionChangeMembersModel(id);
        model.addAttribute("model", viewModel);
        model.addAttribute("removeModel", new RemoveDivisionMemberFormDto());
        model.addAttribute("addModel", new AddDivisionMemberFormDto());
        return "pages/divisions/changeMembers";
    }

    @PostMapping("/remove-members")
    public String removeMembers(@Valid RemoveDivisionMemberFormDto data, BindingResult error){
        if (!error.hasErrors()) divisionMemberService.removeDivisionMember(data);
        return "redirect:/divisions/change-members/"+ data.getDivision().getDivisionSeq();
    }

    @PostMapping("/add-members")
    public String addMembers(@Valid AddDivisionMemberFormDto data, BindingResult error){
        if (!error.hasErrors()) divisionMemberService.addDivisionMember(data);
        return "redirect:/divisions/change-members/"+ data.getDivision().getDivisionSeq();
    }

    @GetMapping("/add")
    public String addDivision(Model model) {
        model.addAttribute("subheader", Subheader.ADD_DIVISION);
        DivisionAddModel divisionAddModel = divisionService.buildDefaultDivisionAddModel();
        model.addAttribute("dataModel", divisionAddModel);
        return "pages/divisions/add_division";
    }

    @PostMapping(value = "/add")
    public String addDivision(@ModelAttribute DivisionAddModel divisionAddModel) {
        Division division = divisionService.addDivision(divisionAddModel);
        switch (divisionAddModel.getAction()) {
            case "saveAndAddNew":
                return "redirect:/divisions/add";
            case "saveAndAddMember" :
                return "redirect:/divisions/change-members/" + division.getDivisionSeq();
            case "saveAndExit" :
                return "redirect:/divisions";
            default:
                return "pages/divisions/divisions";
        }
    }
}
