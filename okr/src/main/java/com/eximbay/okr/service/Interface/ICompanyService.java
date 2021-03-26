package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.*;
import com.eximbay.okr.model.company.*;
import com.eximbay.okr.model.okr.QuarterlyOkrModel;

import java.util.*;

public interface ICompanyService extends ISerivce<CompanyDto, Integer>{
    Optional<CompanyDto> getCompany();
    EditCompanyModel buildEditCompanyModel();
    void updateFormModel(CompanyUpdateFormModel updateFormModel);
    QuarterlyOkrModel buildQuarterlyOkrModel(String quarter, String type, Integer seq);
    CompanyDashboardContentModel buildCompanyDashboardContentModel(String quarter);
    CompanyDashboardResponse getCompanyDashboardModel(String quarter);
    CompanyOkrModel buildCompanyOkrModel(String quarter);
}
