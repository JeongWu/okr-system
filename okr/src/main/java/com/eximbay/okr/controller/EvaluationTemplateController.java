package com.eximbay.okr.controller;

import com.eximbay.okr.api.response.RestResponse;
import com.eximbay.okr.dto.evaluation.EvaluationTemplateDto;
import com.eximbay.okr.model.evaluation.EvaluationTemplateModel;
import com.eximbay.okr.service.Interface.IEvaluationTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("evaluation-template")
@RequiredArgsConstructor
public class EvaluationTemplateController {

    private final IEvaluationTemplateService evaluationTemplateService;

    @GetMapping("/add")
    public String addTemplate(Model model) {
        EvaluationTemplateModel evaluationTemplateModel = evaluationTemplateService.buildEvaluationTemplateModel();
        model.addAttribute("model", evaluationTemplateModel);
        return "pages/evaluation_template/add_template";
    }

    @PostMapping("/add")
    public ResponseEntity<RestResponse> addEvaluationTemplate(@RequestBody EvaluationTemplateDto evaluationTemplateDto) {
        evaluationTemplateService.addEvaluationTemplate(evaluationTemplateDto);
        return ResponseEntity.ok(RestResponse.success());
    }

}
