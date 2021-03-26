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
    List<TeamDto> findActiveTeamsOfMember(MemberDto memberDto);
    List<MemberDto> findActiveMembersOfTeam(TeamDto teamDtos);
    List<TeamDto> addLeaderToTeamList(List<TeamDto> teams);
    Page<TeamWithMembersAndLeaderDto> addMembersAndLeader(Page<Team> teams);

}
