package com.eximbay.okr.dto.okrChecklistGroup;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class ChecklistGroupDatatablesInput extends DataTablesInput {
    private String beginDate;
    private String endDate;
    
}
