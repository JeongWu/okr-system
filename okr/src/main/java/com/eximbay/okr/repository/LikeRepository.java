package com.eximbay.okr.repository;

import com.eximbay.okr.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LikeRepository extends JpaRepository<Like, Integer>, JpaSpecificationExecutor<Like> {
}
