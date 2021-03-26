package com.eximbay.okr.repository;

import com.eximbay.okr.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DivisionRepository extends JpaRepository<Division, Integer>, JpaSpecificationExecutor<Division> {
}

