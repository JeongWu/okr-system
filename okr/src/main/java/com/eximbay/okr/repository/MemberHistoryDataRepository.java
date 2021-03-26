package com.eximbay.okr.repository;

import com.eximbay.okr.entity.MemberHistory;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface MemberHistoryDataRepository extends DataTablesRepository<MemberHistory, Integer>  {    
}
