package com.eximbay.okr.service.Interface;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.dto.TeamHistoryDto;
import com.eximbay.okr.dto.okrScheduleHistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.TeamHistory;

public interface ITeamHistoryDataService extends ISerivce<TeamHistoryDto, Integer>{
	DataTablesOutput<TeamHistory> getDataForDatatables(ScheduleHistoryDatatablesInput input);
	DataTablesOutput<TeamHistory> getDataForDatatablesTeam(TeamDto teamDto, ScheduleHistoryDatatablesInput input);

}
