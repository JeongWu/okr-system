package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.okrschedulehistory.OkrScheduleHistoryDto;
import com.eximbay.okr.dto.okrschedulehistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.OkrScheduleHistory;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface IOkrScheduleHistoryService extends IService<OkrScheduleHistoryDto, Integer> {
    DataTablesOutput<OkrScheduleHistory> getDataForDatatables(ScheduleHistoryDatatablesInput input);
}
