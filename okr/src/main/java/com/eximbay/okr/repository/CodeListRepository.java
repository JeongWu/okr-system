package com.eximbay.okr.repository;

import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.entity.CodeListId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeListRepository extends JpaRepository<CodeList, CodeListId> {

}
