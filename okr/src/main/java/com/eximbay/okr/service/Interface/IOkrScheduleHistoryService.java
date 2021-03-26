package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.okrScheduleHistory.OkrScheduleHistoryDto;
import com.eximbay.okr.dto.okrScheduleHistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.OkrScheduleHistory;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface IOkrScheduleHistoryService extends ISerivce<OkrScheduleHistoryDto, Integer>{
    DataTablesOutput<OkrScheduleHistory> getDataForDatatables(ScheduleHistoryDatatablesInput input);
}
