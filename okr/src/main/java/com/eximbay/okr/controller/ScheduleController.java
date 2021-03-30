package com.eximbay.okr.controller;

import com.eximbay.okr.model.schedule.ScheduleModel;
import com.eximbay.okr.service.Interface.IScheduleService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;


@Controller
@RequestMapping("/schedule")
@AllArgsConstructor
public class ScheduleController {
    private final IScheduleService scheduleService;

    @GetMapping
    public String getModel(Model model) {
        ScheduleModel scheduleModel = scheduleService.buildScheduleModel();
        model.addAttribute("model", scheduleModel);
        return "pages/evaluation/schedule/schedule-list";
    }
}
