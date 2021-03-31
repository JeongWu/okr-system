package com.eximbay.okr.service.Interface;


import com.eximbay.okr.dto.okeschedule.OkrScheduleDto;
import com.eximbay.okr.enumeration.ScheduleType;
import com.eximbay.okr.model.okrSchedule.EditOkrScheduleModel;
import com.eximbay.okr.model.okrSchedule.QuarterlyScheduleUpdateModel;

import java.util.Optional;

public interface IOkrScheduleService extends IService<OkrScheduleDto, Integer> {
    EditOkrScheduleModel buildEditOkrScheduleModel();

    Optional<OkrScheduleDto> findByScheduleType(ScheduleType scheduleType);

    void quarterlyScheduleUpdateModel(QuarterlyScheduleUpdateModel quarterlyScheduleUpdateModel);
}