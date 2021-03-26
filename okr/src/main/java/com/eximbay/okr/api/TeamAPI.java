package com.eximbay.okr.api;

import com.eximbay.okr.service.Interface.ITeamService;

import java.util.List;

import com.eximbay.okr.model.TeamListTableModel;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/teams")
@AllArgsConstructor
public class TeamAPI {
    private final ITeamService teamService;

    @PostMapping("/datatables")
    @ResponseBody
    public List<TeamListTableModel> getTeam() {
        List<TeamListTableModel> teamListViewModels = teamService.buildListTableModel();
        return teamListViewModels;
    }
    
    
}
