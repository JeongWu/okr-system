package com.eximbay.okr.controller;

import com.eximbay.okr.model.company.CompanyOkrModel;
import com.eximbay.okr.model.company.CompanyUpdateFormModel;
import com.eximbay.okr.model.company.CompanyDashboardModel;
import com.eximbay.okr.model.company.EditCompanyModel;
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

import javax.validation.Valid;
import java.util.regex.Matcher;
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

    @GetMapping("/dashboard")
    public String showDashBoard(Model model,String quarter){
        if (quarter == null || !Pattern.compile("^\\d{4}-\\dQ").matcher(quarter).matches()) quarter = "";
        CompanyDashboardModel viewModel = companyService.buildCompanyDashboardModel(quarter);
        model.addAttribute("model", viewModel);
        return "pages/companies/dashboard";
    }

    @GetMapping("/okr")
    public String viewOkr(Model model,String quarter){
        if (quarter == null || !Pattern.compile("^\\d{4}-\\dQ").matcher(quarter).matches())
            quarter = DateTimeUtils.findCurrentQuarter();

        CompanyOkrModel viewModel = companyService.buildCompanyOkrModel(quarter);
        model.addAttribute("model", viewModel);
        return "pages/companies/okr";
    }
}
