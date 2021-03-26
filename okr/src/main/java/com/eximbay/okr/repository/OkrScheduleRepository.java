package com.eximbay.okr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eximbay.okr.entity.OkrSchedule;
import com.eximbay.okr.enumeration.ScheduleType;

public interface OkrScheduleRepository extends JpaRepository<OkrSchedule, Integer> {
	Optional<OkrSchedule> findByScheduleType(ScheduleType scheduleType);
}
