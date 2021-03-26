package com.eximbay.okr.controller;

import com.eximbay.okr.model.DivisionsModel;
import com.eximbay.okr.model.okrScheduleHistory.OkrScheduleHistoriesModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/okr-schedule-histories")
public class OkrScheduleHistoryController {

    @GetMapping
    public String getModel(Model model) {
        OkrScheduleHistoriesModel okrModel = new OkrScheduleHistoriesModel();
        model.addAttribute("model", okrModel);
        return "pages/schedules/schedule-histories";
    }
}
