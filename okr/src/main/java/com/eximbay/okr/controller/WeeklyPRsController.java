package com.eximbay.okr.controller;

import com.eximbay.okr.model.weeklyprs.WeeklyPRsMemberModel;
import com.eximbay.okr.service.Interface.IWeeklyPRsService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weekly-prs")
public class WeeklyPRsController {

    private final IWeeklyPRsService weeklyPRsService;

    @GetMapping("/members")
    public String viewAllMembers(Model model) {
        WeeklyPRsMemberModel viewModel = weeklyPRsService.buildViewAllMembersModel();
        model.addAttribute("model", viewModel);
        return "pages/weeklyprs/members";
    }

}
