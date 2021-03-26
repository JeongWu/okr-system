package com.eximbay.okr.model.company;

import com.eximbay.okr.constant.Subheader;
import lombok.Data;

@Data
public class EditCompanyModel {
    private String subheader = Subheader.COMPANY_EDIT;
    private String mutedHeader;
    private CompanyUpdateFormModel model;
}
