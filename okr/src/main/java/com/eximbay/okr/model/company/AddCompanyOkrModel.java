package com.eximbay.okr.model.company;

import lombok.Data;

@Data
public class AddCompanyOkrModel extends OkrCommonModel {
    private int year;
    private int quarter;
    private String quarterBeginDate;
    private String quarterEndDate;
    private Integer sumProportionOfOtherObjectives;
}
