package com.eximbay.okr.repository;

import java.util.List;

import com.eximbay.okr.entity.OkrCheckListDetail;
import com.eximbay.okr.entity.OkrChecklistGroup;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OkrChecklistDetailRepository extends JpaRepository<OkrCheckListDetail, Integer> {
    List<OkrCheckListDetail> findByOkrChecklistGroup(OkrChecklistGroup okrChecklistGroup);
}
