package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.schedule.EvaluationScheduleDto;
import com.eximbay.okr.model.schedule.ScheduleModel;

public interface IScheduleService extends ISerivce<EvaluationScheduleDto, Integer>{
    ScheduleModel buildScheduleModel();
}