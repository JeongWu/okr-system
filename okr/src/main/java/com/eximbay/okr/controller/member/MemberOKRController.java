package com.eximbay.okr.controller.member;

import com.eximbay.okr.api.response.RestResponse;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.model.member.AddMemberOkrModel;
import com.eximbay.okr.model.member.EditMemberOkrModel;
import com.eximbay.okr.service.Interface.IMemberOKRService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/member/{memberId}/okrs")
public class MemberOKRController {

    private final IMemberOKRService memberOKRService;

    @GetMapping("/add")
    public String addObjectAndKeyResult(@PathVariable(name = "memberId") int teamId, Model model) {
        AddMemberOkrModel addMemberOkrModel = memberOKRService.buildAddMemberOkrModel(teamId);
        model.addAttribute("model", addMemberOkrModel);
        return "pages/members/okrs/member_add_okr";
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> addCompanyOkrs(@RequestBody ObjectiveDto objectiveKeyResultsDto, @PathVariable(name = "memberId") int memberId) {
        memberOKRService.addObjectiveAndKeyResult(memberId, objectiveKeyResultsDto);
        return ResponseEntity.ok(RestResponse.success());
    }

    @GetMapping("/{objectiveId}/edit")
    public String editObjectiveAndKeyResult(Model model, @PathVariable("memberId") int memberId, @PathVariable("objectiveId") int objectiveId) {
        EditMemberOkrModel editMemberOkrModel = memberOKRService.buildEditMemberOkrDataModel(memberId, objectiveId);
        model.addAttribute("model", editMemberOkrModel);
        return "pages/members/okrs/member_edit_okr";
    }
}
