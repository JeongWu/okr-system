package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.checklist.OkrCheckListDetailDto;
import com.eximbay.okr.dto.okrchecklistgroup.OkrChecklistGroupDto;

import java.util.List;

public interface IOkrCheckListDetailService extends IService<OkrCheckListDetailDto, Integer> {
    List<OkrCheckListDetailDto> findByOkrChecklistGroup(OkrChecklistGroupDto okrChecklistGroupDto);
}
