package com.eximbay.okr.controller;

import com.eximbay.okr.model.company.*;
import com.eximbay.okr.service.Interface.ICompanyService;
import com.eximbay.okr.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.regex.Pattern;

@Controller
@AllArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final ICompanyService companyService;

    @GetMapping("/edit")
    public String showEditForm(Model model){
        EditCompanyModel viewModel = companyService.buildEditCompanyModel();
        model.addAttribute("model", viewModel);
        model.addAttribute("dataModel",viewModel.getModel());
        return "pages/companies/edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveCompany(@Validated CompanyUpdateFormModel updateFormModel, BindingResult error){
        if (error.hasErrors()) return "redirect:/companies/edit";
        companyService.updateFormModel(updateFormModel);
        return "redirect:/";
    }

}
