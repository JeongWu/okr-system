package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.dto.TeamHistoryDatatablesInput;
import com.eximbay.okr.dto.TeamHistoryDto;
import com.eximbay.okr.dto.okrScheduleHistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.entity.TeamHistory;
import com.eximbay.okr.repository.TeamHistoryDataRepository;
import com.eximbay.okr.repository.TeamRepository;
import com.eximbay.okr.service.Interface.ITeamHistoryDataService;
import com.eximbay.okr.service.specification.TeamHistoryQuery;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.glasnost.orika.MapperFacade;

@Data
@AllArgsConstructor
@Service
public class TeamHistoryDataServiceImpl implements ITeamHistoryDataService {
	private final TeamHistoryDataRepository teamHistoryRepository;
	private final TeamHistoryQuery teamHistoryQuery;
	private final MapperFacade mapper;

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
    public TeamHistoryDto save(TeamHistoryDto teamHistoryDto) {
        TeamHistory teamHistory = mapper.map(teamHistoryDto, TeamHistory.class);
        teamHistory = teamHistoryRepository.save(teamHistory);
        return mapper.map(teamHistory, TeamHistoryDto.class);
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
