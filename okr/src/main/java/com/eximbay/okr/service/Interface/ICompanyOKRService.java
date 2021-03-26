package com.eximbay.okr.service.Interface;

import com.eximbay.okr.model.company.AddCompanyOkrModel;
import com.eximbay.okr.dto.okr.ObjectiveKeyResultsDto;

public interface ICompanyOKRService {
    AddCompanyOkrModel buildAddOkrDataModel();
    void addObjectiveAndKeyResult(ObjectiveKeyResultsDto objectiveKeyResultsDto);
}
