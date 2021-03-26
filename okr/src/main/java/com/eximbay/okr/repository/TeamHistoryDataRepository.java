package com.eximbay.okr.repository;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eximbay.okr.entity.Team;
import com.eximbay.okr.entity.TeamHistory;

public interface TeamHistoryDataRepository extends DataTablesRepository<TeamHistory, Integer> {
}
