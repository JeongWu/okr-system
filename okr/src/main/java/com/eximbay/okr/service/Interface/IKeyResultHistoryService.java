package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.keyresulthistory.KeyResultHistoryDatatablesInput;
import com.eximbay.okr.dto.keyresulthistory.KeyResultHistoryDto;
import com.eximbay.okr.entity.KeyResultHistory;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface IKeyResultHistoryService extends IService<KeyResultHistoryDto, Integer> {
    DataTablesOutput<KeyResultHistory> getDataForDatatables(KeyResultHistoryDatatablesInput input);
}
