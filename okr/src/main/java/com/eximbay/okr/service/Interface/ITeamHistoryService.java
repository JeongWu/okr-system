package com.eximbay.okr.service.Interface;


import com.eximbay.okr.dto.okrschedulehistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.dto.teamhistory.TeamHistoryDto;
import com.eximbay.okr.entity.TeamHistory;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface ITeamHistoryService extends IService<TeamHistoryDto, Integer> {
    DataTablesOutput<TeamHistory> getDataForDatatables(ScheduleHistoryDatatablesInput input);

    DataTablesOutput<TeamHistory> getDataForDatatablesTeam(TeamDto teamDto, ScheduleHistoryDatatablesInput input);
}
