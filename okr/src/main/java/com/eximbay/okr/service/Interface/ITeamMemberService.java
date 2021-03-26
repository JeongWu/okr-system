package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.*;
import com.eximbay.okr.dto.team.TeamWithMembersAndLeaderDto;
import com.eximbay.okr.entity.*;
import org.springframework.data.domain.Page;
import com.eximbay.okr.model.TeamListTableModel;

import java.util.*;

public interface ITeamMemberService extends ISerivce <TeamMemberDto, TeamMemberId>{

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
