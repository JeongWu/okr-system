package com.eximbay.okr.repository;

import java.util.List;

import com.eximbay.okr.entity.WeeklyPrCard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyPrCardRepository extends JpaRepository<WeeklyPrCard,Integer>{
    List<WeeklyPrCard> findByYear(Integer year);
    List<WeeklyPrCard> findByMemberSeq(Integer memberSeq);
    WeeklyPrCard findByWeeklySeq(Integer weeklySeq);
}
