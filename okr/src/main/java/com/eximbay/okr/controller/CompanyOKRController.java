package com.eximbay.okr.controller;

import com.eximbay.okr.api.response.RestResponse;
import com.eximbay.okr.dto.okr.ObjectiveKeyResultsDto;
import com.eximbay.okr.service.Interface.ICompanyOKRService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/company/okrs")
public class CompanyOKRController {

    private final ICompanyOKRService companyOKRService;

    @GetMapping("/add")
    public String addObjectAndKeyResult(Model model) {
        var addCompanyOkrModel = companyOKRService.buildAddOkrDataModel();
        model.addAttribute("model", addCompanyOkrModel);
        return "pages/companies/okrs/company_add_okr";
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> addCompanyOkrs(@RequestBody ObjectiveKeyResultsDto objectiveKeyResultsDto) {
        companyOKRService.addObjectiveAndKeyResult(objectiveKeyResultsDto);
        return ResponseEntity.ok(RestResponse.success());
    }
}
