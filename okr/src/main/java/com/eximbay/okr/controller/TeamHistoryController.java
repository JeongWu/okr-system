package com.eximbay.okr.controller;

import com.eximbay.okr.model.MemberHistoryModel;
import com.eximbay.okr.model.TeamHistoriesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/team-histories")
public class TeamHistoryController {

    @GetMapping("/{id}")
    public String getDataModel(@PathVariable Integer id, Model model) {
        TeamHistoriesModel teamModel = new TeamHistoriesModel();
        model.addAttribute("model", teamModel);
        model.addAttribute("id", id);
        return "pages/teams/team-histories";
    }
    
    @GetMapping("/team-members/{id}")
    public String getMemberData(@PathVariable Integer id, Model model) {
    	MemberHistoryModel historyModel = new MemberHistoryModel();
    	model.addAttribute("model", historyModel);
        model.addAttribute("id", id);
        return "pages/teams/team-members";
    }
    
}
