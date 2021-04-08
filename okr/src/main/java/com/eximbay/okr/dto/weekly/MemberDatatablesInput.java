package com.eximbay.okr.dto.weekly;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class MemberDatatablesInput extends DataTablesInput {
    private String year;
    private String week;
}