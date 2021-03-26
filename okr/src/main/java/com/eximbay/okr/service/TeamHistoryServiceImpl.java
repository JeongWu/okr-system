package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eximbay.okr.dto.TeamHistoryDto;
import com.eximbay.okr.entity.TeamHistory;
import com.eximbay.okr.repository.TeamHistoryRepository;
import com.eximbay.okr.service.Interface.ITeamHistoryService;

import ma.glasnost.orika.MapperFacade;

@Service
public class TeamHistoryServiceImpl implements ITeamHistoryService {
	
	@Autowired
	private TeamHistoryRepository teamHistoryRepository;
	
    @Autowired
    MapperFacade mapper;

	@Override
    public TeamHistoryDto save(TeamHistoryDto teamDto) {
        TeamHistory teamHistory = mapper.map(teamDto, TeamHistory.class);
        teamHistory = teamHistoryRepository.save(teamHistory);
        return mapper.map(teamHistory, TeamHistoryDto.class);
    }

	@Override
	public List<TeamHistoryDto> findAll() {
        List<TeamHistory> teamHistorys = teamHistoryRepository.findAll();
        return mapper.mapAsList(teamHistorys, TeamHistoryDto.class);
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
	
}
