package com.eximbay.okr.model.member;

import lombok.Data;

@Data
public class AddMemberOkrModel extends MemberOkrCommonModel{
    private int year;
    private int quarter;
    private String quarterBeginDate;
    private String quarterEndDate;
    private Integer sumProportionOfOtherObjectives;
}
