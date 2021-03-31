package com.eximbay.okr.repository;

import com.eximbay.okr.entity.DivisionMember;
import com.eximbay.okr.entity.id.DivisionMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DivisionMemberRepository extends JpaRepository<DivisionMember, DivisionMemberId>, JpaSpecificationExecutor<DivisionMember> {
}
