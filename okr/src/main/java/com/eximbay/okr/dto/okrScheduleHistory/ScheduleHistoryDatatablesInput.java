package com.eximbay.okr.dto.okrScheduleHistory;


import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ScheduleHistoryDatatablesInput extends DataTablesInput {
    private String beginDate;
    private String endDate;
}
