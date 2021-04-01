package com.eximbay.okr.controller;

import com.eximbay.okr.model.evaluationschedule.EvaluationScheduleModel;
import com.eximbay.okr.service.Interface.IEvaluationScheduleService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/evaluation-schedules")
@RequiredArgsConstructor
public class EvaluationScheduleController {
    private final IEvaluationScheduleService scheduleService;

    @GetMapping
    public String getModel(Model model) {
        EvaluationScheduleModel evaluationScheduleModel = scheduleService.buildEvaluationScheduleModel();
        model.addAttribute("model", evaluationScheduleModel);
        return "pages/evaluation/schedule/schedule-list";
    }
}
