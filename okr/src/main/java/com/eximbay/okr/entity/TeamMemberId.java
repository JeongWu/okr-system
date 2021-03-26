package com.eximbay.okr.entity;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@Data
@Embeddable
@EqualsAndHashCode(exclude = { "team", "member"})
public class TeamMemberId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "TEAM_SEQ")
    Team team;

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ")
    Member member;

    @Column(name = "APPLY_BEGIN_DATE", length = 8, nullable = false)
    String applyBeginDate;
}
