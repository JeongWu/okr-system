package com.eximbay.okr.dto.weekly;

import com.eximbay.okr.dto.member.MemberDto;

import lombok.Data;

@Data
public class TestData {
    private Integer memberSeq;
    private Integer weeklySeq;
    private WeeklyPRCardDto weeklyPRCard;
    private MemberDto member;

}
