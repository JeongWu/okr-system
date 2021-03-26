package com.eximbay.okr.repository;

import java.util.List;

import com.eximbay.okr.entity.AssessmentCriteria;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentCriteriaRepository extends JpaRepository<AssessmentCriteria, String> {
    List<AssessmentCriteria> findByGroupCode(String groupCode);
}
