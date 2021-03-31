package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.okrchecklistgroup.OkrChecklistGroupDto;

public interface IOkrCheckListGroupDetailService extends IService<OkrChecklistGroupDto, Integer> {
    OkrChecklistGroupDto findLatestGroupDto(ObjectiveDto objectiveDto);
}
