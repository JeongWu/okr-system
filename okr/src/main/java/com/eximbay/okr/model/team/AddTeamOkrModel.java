package com.eximbay.okr.model.team;

import lombok.Data;

@Data
public class AddTeamOkrModel extends TeamOkrCommonModel {
    private int year;
    private int quarter;
    private String quarterBeginDate;
    private String quarterEndDate;
    private Integer sumProportionOfOtherObjectives;
}
