package com.eximbay.okr.repository;

import com.eximbay.okr.entity.KeyResultCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KeyResultCollaboratorRepository extends JpaRepository<KeyResultCollaborator, Integer>,
        JpaSpecificationExecutor<KeyResultCollaborator> {
}
