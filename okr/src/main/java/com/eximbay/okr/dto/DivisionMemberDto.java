package com.eximbay.okr.dto;

import com.eximbay.okr.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DivisionMemberDto {

    private DivisionMemberId divisionMemberId = new DivisionMemberId();
    private String applyEndDate;
    private String justification;
}
