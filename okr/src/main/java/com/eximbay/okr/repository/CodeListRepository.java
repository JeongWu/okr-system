package com.eximbay.okr.repository;

import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.entity.CodeListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CodeListRepository extends JpaRepository<CodeList, CodeListId>, JpaSpecificationExecutor<CodeList> {
    List<CodeList> findByGroupCodeAndUseFlagOrderBySortOrderAsc(String groupCode, String useFlag);
}