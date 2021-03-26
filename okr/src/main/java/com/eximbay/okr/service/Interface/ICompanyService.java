package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.*;
import com.eximbay.okr.model.company.CompanyDashboardModel;
import com.eximbay.okr.model.company.CompanyOkrModel;
import com.eximbay.okr.model.company.CompanyUpdateFormModel;
import com.eximbay.okr.model.company.EditCompanyModel;

import java.util.*;

public interface ICompanyService extends ISerivce<CompanyDto, Integer>{
    Optional<CompanyDto> getCompany();
    EditCompanyModel buildEditCompanyModel();
    void updateFormModel(CompanyUpdateFormModel updateFormModel);
    CompanyDashboardModel buildCompanyDashboardModel(String quarter);
    CompanyDashboardResponse getCompanyDashboardModel(String quarter);
    CompanyOkrModel buildCompanyOkrModel(String quarter);
}
