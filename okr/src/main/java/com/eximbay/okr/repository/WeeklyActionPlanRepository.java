package com.eximbay.okr.repository;

import java.util.List;

import com.eximbay.okr.entity.WeeklyActionPlan;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyActionPlanRepository  extends JpaRepository<WeeklyActionPlan,Integer>{
    List<WeeklyActionPlan> findByWeeklyPRSeq(Integer weeklyPRSeq);
    List<WeeklyActionPlan> findByWeeklySeq(Integer weeklySeq);
}
