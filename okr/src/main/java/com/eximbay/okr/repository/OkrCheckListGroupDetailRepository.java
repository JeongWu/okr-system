package com.eximbay.okr.repository;

import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.entity.OkrChecklistGroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface OkrCheckListGroupDetailRepository extends JpaRepository<OkrChecklistGroup, Integer> {
    Optional<OkrChecklistGroup> findByGroupSeq(OkrChecklistGroup okrChecklistGroup);
    OkrChecklistGroup findTop1ByObjectiveOrderByCreatedDateDesc(Objective objective);
}
