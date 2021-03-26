package com.eximbay.okr.api;

import com.eximbay.okr.model.team.TeamViewOkrModel;
import com.eximbay.okr.service.Interface.ITeamService;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.eximbay.okr.model.TeamListTableModel;

import com.eximbay.okr.service.TemplateService;
import com.eximbay.okr.utils.DateTimeUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/teams")
@AllArgsConstructor
public class TeamAPI {
    private final ITeamService teamService;
    private final TemplateService templateService;

    @PostMapping("/datatables")
    @ResponseBody
    public List<TeamListTableModel> getTeam() {
        List<TeamListTableModel> teamListViewModels = teamService.buildListTableModel();
        return teamListViewModels;
    }

    @GetMapping("/okrs/quarterly")
    public String viewOkr(Model model, Integer seq, String quarter) {
        if (quarter == null || !Pattern.compile("^\\d{4}-\\dQ").matcher(quarter).matches())
            quarter = DateTimeUtils.findCurrentQuarter();
        TeamViewOkrModel viewModel = teamService.buildTeamOkrModel(seq, quarter);
        Map<String, Object> variables = Map.of("model", viewModel);
        return templateService.buildTemplate(variables, "pages/teams/okr");
    }
    
}
