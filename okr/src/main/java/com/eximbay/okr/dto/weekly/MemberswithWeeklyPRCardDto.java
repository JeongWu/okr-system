package com.eximbay.okr.dto.weekly;

import java.util.List;

import lombok.Data;

@Data
public class MemberswithWeeklyPRCardDto {
    private Integer memberSeq;
    private String name;
    private String localName;
    private List<WeeklyPRCardDto> weeklyPRCards;

}
