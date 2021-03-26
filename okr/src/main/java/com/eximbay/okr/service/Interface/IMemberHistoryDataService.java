package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.MemberHistoryDto;
import com.eximbay.okr.dto.okrScheduleHistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.MemberHistory;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface IMemberHistoryDataService extends ISerivce<MemberHistoryDto, Integer>  {
    DataTablesOutput<MemberHistory> getDataForDatatablesMember(MemberDto memberDto, ScheduleHistoryDatatablesInput input);
    
}
