package com.eximbay.okr.controller;

import com.eximbay.okr.api.response.RestResponse;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.service.Interface.ICompanyOKRService;
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
@RequestMapping("/companies/okrs")
public class CompanyOKRController {

    private final ICompanyOKRService companyOKRService;

    @GetMapping("/add")
    public String addObjectAndKeyResult(Model model) {
        var addCompanyOkrModel = companyOKRService.buildAddOkrDataModel();
        model.addAttribute("model", addCompanyOkrModel);
        return "pages/companies/okrs/company_add_okr";
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> addCompanyOkrs(@RequestBody ObjectiveDto objectiveDto) {
        companyOKRService.addObjectiveAndKeyResult(objectiveDto);
        return ResponseEntity.ok(RestResponse.success());
    }

    @GetMapping("/{objectiveId}/edit")
    public String editObjectiveAndKeyResult(Model model, @PathVariable(name = "objectiveId") int objectiveId) {
        var editCompanyOkrModel = companyOKRService.buildEditOkrDataModel(objectiveId);
        model.addAttribute("model", editCompanyOkrModel);
        return "pages/companies/okrs/company_edit_okr";
    }
}
