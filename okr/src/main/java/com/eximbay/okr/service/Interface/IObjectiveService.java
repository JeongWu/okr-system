package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.*;

import java.util.*;

public interface IObjectiveService extends ISerivce<ObjectiveDto, Integer>{
    List<ObjectiveDto> findAllInUse();
}
