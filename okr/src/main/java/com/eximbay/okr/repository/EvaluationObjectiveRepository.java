package com.eximbay.okr.repository;

import com.eximbay.okr.entity.EvaluationObjective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EvaluationObjectiveRepository extends JpaRepository<EvaluationObjective, Integer>, JpaSpecificationExecutor<EvaluationObjective> {
}
