package com.eximbay.okr.dto.keyresulthistory;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class KeyResultHistoryDatatablesInput extends DataTablesInput {
    private Integer keyResultSeq;
}
