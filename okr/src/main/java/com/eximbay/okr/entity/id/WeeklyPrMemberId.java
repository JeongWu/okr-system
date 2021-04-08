package com.eximbay.okr.entity.id;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.WeeklyPRCard;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Embeddable
@EqualsAndHashCode(exclude = { "weeklyprcard", "member"})
@ToString(exclude = { "weeklyprcard", "member"} )
public class WeeklyPrMemberId {
    
    @ManyToOne
    @JoinColumn(name = "WEEKLY_SEQ")
    WeeklyPRCard weeklyPRCard;

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ")
    Member member;
}
