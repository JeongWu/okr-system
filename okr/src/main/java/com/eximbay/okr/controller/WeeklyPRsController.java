package com.eximbay.okr.controller;

import com.eximbay.okr.model.weeklypr.WeeklyPRMemberModel;
import com.eximbay.okr.service.Interface.IWeeklyPRService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weekly-prs")
public class WeeklyPRsController {

    private final IWeeklyPRService weeklyPRService;

    @GetMapping("/members")
    public String viewAllMembers(Model model) {
        WeeklyPRMemberModel viewModel = weeklyPRService.buildViewAllMembersModel();
        model.addAttribute("model", viewModel);
        return "pages/weeklypr/members";
    }
    @GetMapping("/test")
    public String test(Model model) {
        WeeklyPRMemberModel viewModel = weeklyPRService.buildViewAllMembersModel();
        model.addAttribute("model", viewModel);
        return "pages/weeklypr/test";
    }
    @GetMapping("/data")
    public String data(Model model) {
        WeeklyPRMemberModel viewModel = weeklyPRService.buildViewAllMembersModel();
        model.addAttribute("model", viewModel);
        return "pages/weeklypr/data";
    }

}
