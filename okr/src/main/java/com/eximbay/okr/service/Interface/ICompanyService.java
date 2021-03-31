package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.company.CompanyDashboardResponse;
import com.eximbay.okr.dto.company.CompanyDto;
import com.eximbay.okr.model.company.CompanyDashboardContentModel;
import com.eximbay.okr.model.company.CompanyOkrModel;
import com.eximbay.okr.model.company.CompanyUpdateFormModel;
import com.eximbay.okr.model.company.EditCompanyModel;
import com.eximbay.okr.model.okr.QuarterlyOkrModel;

import java.util.Optional;

public interface ICompanyService extends IService<CompanyDto, Integer> {
    Optional<CompanyDto> getCompany();

    EditCompanyModel buildEditCompanyModel();

    void updateFormModel(CompanyUpdateFormModel updateFormModel);

    QuarterlyOkrModel buildQuarterlyOkrModel(String quarter, String type, Integer seq);

    CompanyDashboardContentModel buildCompanyDashboardContentModel(String quarter);

    CompanyDashboardResponse getCompanyDashboardModel(String quarter);

    CompanyOkrModel buildCompanyOkrModel(String quarter);
}
