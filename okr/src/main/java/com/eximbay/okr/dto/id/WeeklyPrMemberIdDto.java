package com.eximbay.okr.dto.id;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.weekly.WeeklyPRCardDto;

import lombok.Data;

@Data
public class WeeklyPrMemberIdDto {
    WeeklyPRCardDto weeklyPRCard;
    MemberDto memberDto;
}
