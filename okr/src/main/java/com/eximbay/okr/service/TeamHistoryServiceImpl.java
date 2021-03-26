package com.eximbay.okr.service;

import com.eximbay.okr.dto.DivisionHistoryDto;
import com.eximbay.okr.dto.TeamHistoryDto;
import com.eximbay.okr.entity.DivisionHistory;
import com.eximbay.okr.entity.TeamHistory;
import com.eximbay.okr.repository.DivisionHistoryRepository;
import com.eximbay.okr.repository.TeamHistoryRepository;
import com.eximbay.okr.service.Interface.IDivisionHistoryService;
import com.eximbay.okr.service.Interface.ITeamHistoryService;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamHistoryServiceImpl implements ITeamHistoryService {

    @Autowired
    TeamHistoryRepository teamHistoryRepository;

    @Autowired
    MapperFacade mapper;

    @Override
    public List<TeamHistoryDto> findAll() {
        List<TeamHistory> teamHistories = teamHistoryRepository.findAll();
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

}
