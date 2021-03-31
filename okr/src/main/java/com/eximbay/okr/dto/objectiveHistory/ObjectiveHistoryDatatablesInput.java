package com.eximbay.okr.dto.objectivehistory;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ObjectiveHistoryDatatablesInput  extends DataTablesInput {
    private Integer objectiveSeq;
}
