package com.eximbay.okr.api;
import javax.validation.Valid;

import com.eximbay.okr.dto.evaluationschedule.EvaluationScheduleDatatablesInput;
import com.eximbay.okr.entity.EvaluationSchedule;
import com.eximbay.okr.service.Interface.IEvaluationScheduleService;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/schedules")
public class EvaluationScheduleAPI {

    private final IEvaluationScheduleService evaluationScheduleService;

    @PostMapping("/datatables")
    public DataTablesOutput<EvaluationSchedule> getAll(@Valid @RequestBody EvaluationScheduleDatatablesInput input) {
        DataTablesOutput<EvaluationSchedule> evaluationSchedules = evaluationScheduleService.getDataForDatatables(input);
        return evaluationSchedules;
    }
    
}
