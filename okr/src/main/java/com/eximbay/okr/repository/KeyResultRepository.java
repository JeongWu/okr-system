package com.eximbay.okr.repository;

import com.eximbay.okr.entity.KeyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KeyResultRepository extends JpaRepository<KeyResult, Integer>, JpaSpecificationExecutor<KeyResult> {
}
