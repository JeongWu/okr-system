package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.schedule.EvaluationScheduleDto;
import com.eximbay.okr.dto.schedule.ScheduleDatatablesInput;
import com.eximbay.okr.entity.EvaluationSchedule;
import com.eximbay.okr.model.schedule.ScheduleModel;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface IScheduleService extends ISerivce<EvaluationScheduleDto, Integer>{
    ScheduleModel buildScheduleModel();
    DataTablesOutput<EvaluationSchedule> getDataForDatatables(ScheduleDatatablesInput input);
}