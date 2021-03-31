package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.memberhistory.MemberHistoryDto;
import com.eximbay.okr.dto.okrschedulehistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.MemberHistory;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface IMemberHistoryDataService extends IService<MemberHistoryDto, Integer> {
    DataTablesOutput<MemberHistory> getDataForDatatablesMember(MemberDto memberDto, ScheduleHistoryDatatablesInput input);
}
