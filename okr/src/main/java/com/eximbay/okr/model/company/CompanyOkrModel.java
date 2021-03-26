package com.eximbay.okr.model.company;

import com.eximbay.okr.constant.MutedHeader;
import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.model.objective.CompanyObjectiveOkrModel;
import lombok.Data;

import java.util.List;

@Data
public class CompanyOkrModel {
    private String subheader = Subheader.COMPANY_OKR;
    private String mutedHeader = MutedHeader.COMPANY_OKR;
    private List<CompanyObjectiveOkrModel> objectives;
    private boolean editable;
    private String quarter;
}
