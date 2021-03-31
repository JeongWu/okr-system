package com.eximbay.okr.entity;

import com.eximbay.okr.entity.id.DivisionMemberId;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "division_member")
@Entity

public class DivisionMember extends AbstractAuditable {

    @EmbeddedId
    private DivisionMemberId divisionMemberId;

    @Column(name = "APPLY_END_DATE", length = 8, nullable = false)
    private String applyEndDate;

    @Column(name = "JUSTIFICATION")
    private String justification;
}
