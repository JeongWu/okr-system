package com.eximbay.okr.repository;

import java.util.List;
import java.util.Optional;

import com.eximbay.okr.entity.CodeGroup;
import com.eximbay.okr.entity.CodeList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, Integer> {

    Optional<CodeGroup> findByGroupCode(String code);
    
}

