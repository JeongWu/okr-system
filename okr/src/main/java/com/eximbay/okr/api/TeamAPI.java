package com.eximbay.okr.api;

import com.eximbay.okr.model.TeamListTableModel;
import com.eximbay.okr.model.team.TeamViewOkrModel;
import com.eximbay.okr.service.Interface.ITeamService;
import com.eximbay.okr.service.TemplateService;
import com.eximbay.okr.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/teams")
public class TeamAPI {

    private final TemplateService templateService;
    private final ITeamService teamService;

    @PostMapping("/datatables")
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
