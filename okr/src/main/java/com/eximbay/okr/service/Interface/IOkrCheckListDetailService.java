package com.eximbay.okr.service.Interface;

import java.util.List;

import com.eximbay.okr.dto.OkrCheckListDetailDto;
import com.eximbay.okr.dto.okrChecklistGroup.OkrChecklistGroupDto;

public interface IOkrCheckListDetailService extends ISerivce<OkrCheckListDetailDto, Integer> {
    List<OkrCheckListDetailDto> findByOkrChecklistGroup(OkrChecklistGroupDto okrChecklistGroupDto);    
}
