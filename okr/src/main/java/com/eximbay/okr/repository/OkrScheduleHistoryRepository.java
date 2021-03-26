package com.eximbay.okr.repository;

import com.eximbay.okr.entity.OkrScheduleHistory;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

public interface OkrScheduleHistoryRepository extends DataTablesRepository<OkrScheduleHistory, Integer> {
}
