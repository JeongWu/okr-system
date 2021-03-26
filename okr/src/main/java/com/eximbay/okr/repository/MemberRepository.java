package com.eximbay.okr.repository;

import com.eximbay.okr.entity.DivisionMember;
import com.eximbay.okr.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer>, JpaSpecificationExecutor<Member> {
    Optional<Member> findByEmail(String email);
    List<Member> findAll();
    Member findByMemberSeq(int memberSeq);
}
