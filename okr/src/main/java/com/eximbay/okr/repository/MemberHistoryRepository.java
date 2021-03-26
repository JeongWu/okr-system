package com.eximbay.okr.repository;

import java.time.Instant;
import java.util.List;

import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.MemberHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHistoryRepository extends JpaRepository<MemberHistory, Integer> {
    List<MemberHistory> findByName(String name);
    List<MemberHistory> findByMember(Member member);
    Page<MemberHistory> findByMemberAndUpdatedDateBetweenAndJustificationContaining
    (Member member, Instant from, Instant to, String justification, Pageable pageable);
    List<MemberHistory> findByMemberAndUpdatedDateBetweenAndJustificationContaining(Member member, Instant from, Instant to, String justification);
}
