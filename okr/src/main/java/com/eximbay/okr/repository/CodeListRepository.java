package com.eximbay.okr.repository;

import java.util.List;

import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.entity.id.CodeListId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CodeListRepository extends JpaRepository<CodeList, CodeListId>, JpaSpecificationExecutor<CodeList> {
    List<CodeList> findByGroupCodeAndUseFlagOrderBySortOrderAsc(String groupCode, String useFlag);
}