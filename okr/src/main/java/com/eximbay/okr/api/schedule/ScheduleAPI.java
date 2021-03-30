package com.eximbay.okr.api.schedule;
import javax.validation.Valid;

import com.eximbay.okr.dto.schedule.ScheduleDatatablesInput;
import com.eximbay.okr.entity.EvaluationSchedule;
import com.eximbay.okr.service.Interface.IScheduleService;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("api/schedule")
public class ScheduleAPI {
    private final IScheduleService scheduleService;

    @PostMapping("/datatables")
    public DataTablesOutput<EvaluationSchedule> getAll(@Valid @RequestBody ScheduleDatatablesInput input) {
        DataTablesOutput<EvaluationSchedule> evaluationSchedules = scheduleService.getDataForDatatables(input);
        return evaluationSchedules;
    }
}
