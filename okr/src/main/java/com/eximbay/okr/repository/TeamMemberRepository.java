package com.eximbay.okr.repository;

import com.eximbay.okr.entity.TeamMember;
import com.eximbay.okr.entity.id.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId>, JpaSpecificationExecutor<TeamMember> {
}
