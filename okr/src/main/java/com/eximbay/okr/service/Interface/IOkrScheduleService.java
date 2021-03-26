package com.eximbay.okr.service.Interface;


import java.util.Optional;

import com.eximbay.okr.dto.okeSchedule.OkrScheduleDto;
import com.eximbay.okr.enumeration.ScheduleType;
import com.eximbay.okr.model.okrSchedule.*;

public interface IOkrScheduleService extends ISerivce<OkrScheduleDto, Integer>{
	EditOkrScheduleModel buildEditOkrScheduleModel();
	Optional<OkrScheduleDto> findByScheduleType(ScheduleType scheduleType);
	void quarterlyScheduleUpdateModel(QuarterlyScheduleUpdateModel quarterlyScheduleUpdateModel);
}