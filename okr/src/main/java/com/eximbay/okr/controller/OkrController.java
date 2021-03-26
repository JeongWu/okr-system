package com.eximbay.okr.controller;

import com.eximbay.okr.model.okr.QuarterlyOkrModel;
import com.eximbay.okr.service.Interface.ICompanyService;
import com.eximbay.okr.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.regex.Pattern;

@Controller
@RequestMapping("/okrs")
@AllArgsConstructor
public class OkrController {
    private final ICompanyService companyService;

    @GetMapping("/quarterly")
    public String showDashBoard(Model model, String quarter, String type, Integer seq){
        if (quarter == null || !Pattern.compile("^\\d{4}-\\dQ").matcher(quarter).matches())
            quarter = DateTimeUtils.findCurrentQuarter();
        QuarterlyOkrModel viewModel = companyService.buildQuarterlyOkrModel(quarter, type, seq);
        model.addAttribute("model", viewModel);
        return "pages/okrs/okrs-quarterly";
    }
}
