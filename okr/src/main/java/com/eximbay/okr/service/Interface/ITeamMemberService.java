package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.member.MemberWithActiveDto;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.dto.team.TeamWithMembersAndLeaderDto;
import com.eximbay.okr.dto.teammember.TeamMemberDto;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.entity.id.TeamMemberId;
import com.eximbay.okr.model.TeamListTableModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ITeamMemberService extends IService<TeamMemberDto, TeamMemberId> {

    List<TeamMemberDto> findCurrentlyValid(List<TeamMemberDto> teamDtos);

    Optional<MemberDto> findTeamLeaderOrManager(List<TeamMemberDto> teamMemberDtos);

    Optional<MemberDto> findTeamLeaderOrManager(Integer teamSeq);

    boolean isCurrentMemberLeaderOrManager(Integer teamSeq);

    List<TeamDto> findActiveTeamsOfMember(MemberDto memberDto);

    List<TeamDto> addLeaderToTeamList(List<TeamDto> teams);

    Page<TeamWithMembersAndLeaderDto> addMembersAndLeader(Page<Team> teams);

    List<TeamListTableModel> addMembersAndLeaderForDataTable(List<TeamDto> teams);

    boolean isCurrentMemberCanEditTeamOkr(Integer teamSeq);

    List<TeamMemberDto> findSearchBelong(MemberDto memberDto);

    List<TeamMemberDto> findSearchBelongTeam(TeamDto teamDto);

    List<TeamDto> findTeamsByMemberSeq(Integer memberSeq);

    Page<MemberWithActiveDto> addActiveMember(Page<Member> members);

}
