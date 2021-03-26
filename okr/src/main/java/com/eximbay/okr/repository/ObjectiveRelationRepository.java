package com.eximbay.okr.repository;

import com.eximbay.okr.entity.ObjectiveRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ObjectiveRelationRepository extends JpaRepository<ObjectiveRelation, Integer>, JpaSpecificationExecutor<ObjectiveRelation> {
}
