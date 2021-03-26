package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.*;
import com.eximbay.okr.dto.objective.UpdateObjectivePriorityRequest;
import com.eximbay.okr.model.objective.CompanyObjectiveOkrModel;

import java.util.*;

public interface IObjectiveService extends ISerivce<ObjectiveDto, Integer>{
    List<ObjectiveDto> findAllInUse();
    List<String> findAllQuarters();
    List<ObjectiveDto> findAllInQuarter(String quarter);
    List<CompanyObjectiveOkrModel> findAllCompanyObjectiveOkrInQuarter(String quarterString);
    void updateObjectivePriority(UpdateObjectivePriorityRequest request);
}
