package com.eximbay.okr.controller.team;

import com.eximbay.okr.api.response.RestResponse;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.model.team.AddTeamOkrModel;
import com.eximbay.okr.service.Interface.ITeamOKRService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/team/{teamId}/okrs")
public class TeamOKRController {

    private final ITeamOKRService teamOKRService;

    @GetMapping("/add")
    public String addObjectAndKeyResult(@PathVariable(name = "teamId") int teamId, Model model) {
        AddTeamOkrModel addTeamOkrModel = teamOKRService.buildAddTeamOkrModel(teamId);
        model.addAttribute("model", addTeamOkrModel);
        return "pages/teams/okrs/team_add_okr";
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> addCompanyOkrs(@RequestBody ObjectiveDto objectiveDto, @PathVariable(name = "teamId") int teamId) {
        teamOKRService.addObjectiveAndKeyResult(teamId, objectiveDto);
        return ResponseEntity.ok(RestResponse.success());
    }
}
