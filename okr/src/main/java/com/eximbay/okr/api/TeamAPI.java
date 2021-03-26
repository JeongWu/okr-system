package com.eximbay.okr.api;

import com.eximbay.okr.service.Interface.ITeamService;

import java.util.List;

import com.eximbay.okr.model.AllTeamUpdateModel;
import com.eximbay.okr.model.TeamListTableModel;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/teams")
@AllArgsConstructor
public class TeamAPI {
    private final ITeamService teamService;

    @RequestMapping("/datatables")
    @ResponseBody
    public List<TeamListTableModel> getTeam() {
        List<TeamListTableModel> teamListViewModels = teamService.buildListTableModel();
        return teamListViewModels;
    }
    
    
}
