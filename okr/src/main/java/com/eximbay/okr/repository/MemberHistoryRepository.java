package com.eximbay.okr.repository;

import java.util.List;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.MemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHistoryRepository extends JpaRepository<MemberHistory, Integer>{
    List<MemberHistory> findByName(String name);
    List<MemberHistory> findByMember(Member member);
}
