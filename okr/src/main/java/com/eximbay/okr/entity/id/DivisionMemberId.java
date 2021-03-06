package com.eximbay.okr.entity.id;

import com.eximbay.okr.entity.Division;
import com.eximbay.okr.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
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
