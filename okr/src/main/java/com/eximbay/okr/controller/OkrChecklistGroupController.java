package com.eximbay.okr.controller;

import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.model.okrChecklistGroup.OkrChecklistGroupsModel;
import com.eximbay.okr.service.Interface.ITeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/okr-checklist-groups")
public class OkrChecklistGroupController {

    private final ITeamService teamService;
    
    @GetMapping
    public String getModel(Model model){
        List<TeamDto> teams = teamService.findAll();
        OkrChecklistGroupsModel okrModel = new OkrChecklistGroupsModel();
        List<String> teamName=teams.stream().map(t->t.getName()).collect(Collectors.toList());
        model.addAttribute("teamName", teamName);
        model.addAttribute("team", teams);
        model.addAttribute("model", okrModel);
        
        return "pages/checklists/checklist-groups";
    }
    
}
