package com.eximbay.okr.controller.weeklypr;

import com.eximbay.okr.model.weeklypr.WeeklyPrMemberModel;
import com.eximbay.okr.service.Interface.IWeeklyPrService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weekly-pr")
public class WeeklyPrController {

    private final IWeeklyPrService weeklyPrService;

    @GetMapping("member/{memberId}")
    public String viewPlanAndReviewDetail(Model model) {

        return "pages/weekly_pr/view_pr_details";
    }

    @GetMapping("/members")
         String viewAllMembers(Model model) {
            WeeklyPrMemberModel viewModel = weeklyPrService.buildViewAllMembersModel();
            model.addAttribute("model", viewModel);
            return "pages/weekly_pr/view_all_members";
    }
}
