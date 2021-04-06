package com.eximbay.okr.dto.weeklypr;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
// @ToString(callSuper = true)
@Data
public class MemberDatatablesInput extends DataTablesInput {
    private Integer year;
    private Integer week;
}
