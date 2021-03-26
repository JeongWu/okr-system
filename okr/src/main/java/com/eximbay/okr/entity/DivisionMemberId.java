package com.eximbay.okr.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
@ToString(exclude = { "division", "member"})
@EqualsAndHashCode(exclude = { "division", "member"})
public class DivisionMemberId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "DIVISION_SEQ")
    Division division;

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ")
    Member member;

    @Column(name = "APPLY_BEGIN_DATE", length = 8, nullable = false)
    String applyBeginDate;
}
