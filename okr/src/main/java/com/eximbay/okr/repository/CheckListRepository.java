package com.eximbay.okr.repository;

import com.eximbay.okr.entity.CheckList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList, Integer>{
    
}
