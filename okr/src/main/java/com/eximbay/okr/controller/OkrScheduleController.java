package com.eximbay.okr.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eximbay.okr.model.okrSchedule.*;
import com.eximbay.okr.service.Interface.IOkrScheduleService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/okr-schedule-edit")
public class OkrScheduleController {
    private final IOkrScheduleService okrScheduleService;

	@GetMapping
	public String showEditForm(Model model) {
		EditOkrScheduleModel viewModel = okrScheduleService.buildEditOkrScheduleModel();
		model.addAttribute("model", viewModel);
		model.addAttribute("dataQuarterlyModel", viewModel.getQuarterlyModel());
		model.addAttribute("dataMonthlyModel", viewModel.getMonthlyModel());
		model.addAttribute("dataWeeklyModel", viewModel.getWeeklyModel());
        return "pages/schedules/schedules-edit";
	}

	@PostMapping("/save")
	public String saveSchedule(@Validated QuarterlyScheduleUpdateModel quarterlyScheduleUpdateModel, BindingResult error) {
		if (error.hasErrors())
			return "redirect:/okr-schedule-edit" + quarterlyScheduleUpdateModel.getScheduleSeq();
		okrScheduleService.quarterlyScheduleUpdateModel(quarterlyScheduleUpdateModel);
		return "redirect:/okr-schedule-edit";
	}


	
}
