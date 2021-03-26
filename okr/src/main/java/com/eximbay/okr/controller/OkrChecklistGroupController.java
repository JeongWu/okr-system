package com.eximbay.okr.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.model.okrChecklistGroup.OkrChecklistGroupsModel;
import com.eximbay.okr.repository.TeamRepository;
import com.eximbay.okr.service.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
@RequestMapping("/okr-checklist-groups")
public class OkrChecklistGroupController {

    private final TeamServiceImpl teamService;
    
    @GetMapping
    public String getModel(Model model){
        List<TeamDto> teams = teamService.findAll();
        OkrChecklistGroupsModel okrModel = new OkrChecklistGroupsModel();
        model.addAttribute("team", teams);
        model.addAttribute("model", okrModel);

        List<String> teamName=teams.stream().map(t->t.getName()).collect(Collectors.toList());
        model.addAttribute("teamName", teamName);

        return "pages/checklists/checklist-groups";
    }
    
}
