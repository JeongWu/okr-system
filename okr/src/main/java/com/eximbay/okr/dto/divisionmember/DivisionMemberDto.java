package com.eximbay.okr.dto.divisionmember;

import com.eximbay.okr.entity.id.DivisionMemberId;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DivisionMemberDto extends AbstractAuditableDto {

    private DivisionMemberId divisionMemberId = new DivisionMemberId();
    private String applyEndDate;
    private String justification;
}
