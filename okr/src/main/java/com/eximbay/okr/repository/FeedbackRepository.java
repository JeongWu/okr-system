package com.eximbay.okr.repository;

import com.eximbay.okr.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer>, JpaSpecificationExecutor<Feedback> {
    List<Feedback> findTop10ByOrderByCreatedDateDesc();
}
