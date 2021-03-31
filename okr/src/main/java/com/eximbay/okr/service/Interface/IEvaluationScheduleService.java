package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.evaluationschedule.EvaluationScheduleDto;
import com.eximbay.okr.dto.evaluationschedule.EvaluationScheduleDatatablesInput;
import com.eximbay.okr.entity.EvaluationSchedule;
import com.eximbay.okr.model.evaluationschedule.EvaluationScheduleModel;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface IEvaluationScheduleService extends IService<EvaluationScheduleDto, Integer> {
    EvaluationScheduleModel buildEvaluationScheduleModel();
    DataTablesOutput<EvaluationSchedule> getDataForDatatables(EvaluationScheduleDatatablesInput input);
}