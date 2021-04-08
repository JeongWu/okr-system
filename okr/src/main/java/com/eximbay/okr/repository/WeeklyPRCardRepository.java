package com.eximbay.okr.repository;

import java.util.List;

import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.WeeklyPRCard;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyPRCardRepository extends JpaRepository<WeeklyPRCard,Integer>,DataTablesRepository<WeeklyPRCard, Integer> {

    List<WeeklyPRCard> findByYear(Integer year);
    List<WeeklyPRCard> findByMember(Member member);
    List<WeeklyPRCard> findByMemberSeq(Integer memberSeq);
    WeeklyPRCard findByWeeklySeq(Integer weeklySeq);
    
}
