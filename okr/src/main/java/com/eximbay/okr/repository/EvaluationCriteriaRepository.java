package com.eximbay.okr.repository;

import java.util.List;

import com.eximbay.okr.entity.EvaluationCriteria;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteria, String> {
    List<EvaluationCriteria> findByGroupCode(String groupCode);
    List<EvaluationCriteria> findByGroupCodeLike(String groupCodeLike);
}
