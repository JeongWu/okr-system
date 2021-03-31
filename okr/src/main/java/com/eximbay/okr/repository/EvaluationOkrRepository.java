package com.eximbay.okr.repository;

import com.eximbay.okr.entity.EvaluationOkr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EvaluationOkrRepository extends JpaRepository<EvaluationOkr, Integer>, JpaSpecificationExecutor<EvaluationOkr> {
}
