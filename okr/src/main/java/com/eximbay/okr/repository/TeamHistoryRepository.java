package com.eximbay.okr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eximbay.okr.entity.Team;
import com.eximbay.okr.entity.TeamHistory;

public interface TeamHistoryRepository extends JpaRepository<TeamHistory, Integer> {
	List<TeamHistory> findByTeam(Team team);
}
