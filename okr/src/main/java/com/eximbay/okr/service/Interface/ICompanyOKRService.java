package com.eximbay.okr.service.Interface;

import com.eximbay.okr.model.company.AddCompanyOkrModel;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.model.company.EditCompanyOkrModel;

public interface ICompanyOKRService {
    AddCompanyOkrModel buildAddOkrDataModel();
    void addObjectiveAndKeyResult(ObjectiveDto objectiveKeyResultsDto);
    EditCompanyOkrModel buildEditOkrDataModel(int objectiveId);
}
