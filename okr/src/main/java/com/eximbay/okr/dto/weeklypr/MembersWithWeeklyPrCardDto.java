package com.eximbay.okr.dto.weeklypr;

import com.eximbay.okr.dto.member.MemberDto;

import lombok.Data;

@Data
public class MembersWithWeeklyPrCardDto {
        private Integer memberSeq;
        private Integer weeklySeq;
        private WeeklyPrCardDto weeklyPrCard;
        private MemberDto member;
}
