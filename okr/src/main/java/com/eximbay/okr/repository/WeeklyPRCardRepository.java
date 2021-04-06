package com.eximbay.okr.repository;

import java.util.List;

import com.eximbay.okr.entity.WeeklyPRCard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyPRCardRepository extends JpaRepository<WeeklyPRCard,Integer>{

    List<WeeklyPRCard> findByYear(Integer year);
    
}
