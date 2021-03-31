package com.eximbay.okr.repository;

import com.eximbay.okr.entity.EvaluationSchedule;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface EvaluationScheduleRepository extends DataTablesRepository<EvaluationSchedule, Integer> {
}
