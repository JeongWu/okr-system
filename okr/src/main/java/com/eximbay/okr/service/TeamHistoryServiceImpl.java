package com.eximbay.okr.service;

import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.dto.teamhistory.TeamHistoryDto;
import com.eximbay.okr.dto.okrschedulehistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.entity.TeamHistory;
import com.eximbay.okr.repository.TeamHistoryDataRepository;
import com.eximbay.okr.service.Interface.ITeamHistoryService;
import com.eximbay.okr.service.specification.TeamHistoryQuery;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamHistoryServiceImpl implements ITeamHistoryService {

	private final MapperFacade mapper;
	private final TeamHistoryQuery teamHistoryQuery;
	private final TeamHistoryDataRepository teamHistoryRepository;

	@Override
    public TeamHistoryDto save(TeamHistoryDto teamDto) {
        TeamHistory teamHistory = mapper.map(teamDto, TeamHistory.class);
        teamHistory = teamHistoryRepository.save(teamHistory);
        return mapper.map(teamHistory, TeamHistoryDto.class);
    }

	@Override
	public List<TeamHistoryDto> findAll() {
        List<TeamHistory> teamHistories = Lists.newArrayList(teamHistoryRepository.findAll());
        return mapper.mapAsList(teamHistories, TeamHistoryDto.class);
	}

	@Override
	public Optional<TeamHistoryDto> findById(Integer id) {        
        Optional<TeamHistory> teamHistory = teamHistoryRepository.findById(id);
        return teamHistory.map(m-> mapper.map(m, TeamHistoryDto.class));
	}

	@Override
	public void remove(TeamHistoryDto teamHistoryDto) {
		TeamHistory teamHistory = mapper.map(teamHistoryDto, TeamHistory.class);
		teamHistoryRepository.delete(teamHistory);
	}

	@Override
	public DataTablesOutput<TeamHistory> getDataForDatatables(ScheduleHistoryDatatablesInput input) {
		DataTablesOutput<TeamHistory> output = teamHistoryRepository.findAll(input,
				teamHistoryQuery.buildQueryForDatatables(input));
		return output;
	}

	@Override
	public DataTablesOutput<TeamHistory> getDataForDatatablesTeam(TeamDto teamDto, ScheduleHistoryDatatablesInput input) {
		Team team = mapper.map(teamDto, Team.class);
		DataTablesOutput<TeamHistory> output = teamHistoryRepository.findAll(input,
				teamHistoryQuery.buildQueryForDatatablesTeam(team, input));
		return output;
	}
	
}
