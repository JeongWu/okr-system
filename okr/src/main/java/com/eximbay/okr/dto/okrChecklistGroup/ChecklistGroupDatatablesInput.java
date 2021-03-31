package com.eximbay.okr.dto.okrchecklistgroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ChecklistGroupDatatablesInput extends DataTablesInput {
    private String beginDate;
    private String endDate;
    
}
