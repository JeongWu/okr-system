package com.eximbay.okr.repository;

import com.eximbay.okr.entity.Team;

import org.hibernate.annotations.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface TeamRepository extends JpaRepository<Team, Integer>, JpaSpecificationExecutor<Team> {
    List<Team> findByUseFlag(String useFlag);
    int countByUseFlag(String useFlag);
    Page<Team> findByUseFlag(String useFlag, Pageable pageable);
    List<Team> findAll();
    
    
}
