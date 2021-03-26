package com.eximbay.okr.model.company;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.CodeListDto;
import com.eximbay.okr.entity.Company;
import lombok.Data;

import java.util.List;

@Data
public class AddCompanyOkrModel {
    private String subheader = Subheader.ADD_OKR;
    private String mutedHeader = "Company Name";
    private Company company;
    private List<CodeListDto> objectiveLevels;
    private int year;
    private int quarter;
    private String quarterBeginDate;
    private String quarterEndDate;
    private List<CodeListDto> taskTypes;
    private List<CodeListDto> taskMetrics;
    private List<CodeListDto> taskIndicators;
}
