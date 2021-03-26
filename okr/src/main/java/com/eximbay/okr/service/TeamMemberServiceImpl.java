package com.eximbay.okr.service;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.DivisionDto;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.dto.TeamMemberDto;
import com.eximbay.okr.dto.team.TeamWithMembersAndLeaderDto;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.entity.TeamMember;
import com.eximbay.okr.entity.TeamMemberId;
import com.eximbay.okr.model.TeamListTableModel;
import com.eximbay.okr.repository.TeamMemberRepository;
import com.eximbay.okr.service.Interface.ITeamMemberService;
import com.eximbay.okr.service.specification.TeamMemberQuery;
import com.eximbay.okr.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamMemberServiceImpl implements ITeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberQuery teamMemberQuery;
    private final MapperFacade mapper;

    @Override
    public List<TeamMemberDto> findAll() {
        List<TeamMember> teamMembers = teamMemberRepository.findAll();
        return mapper.mapAsList(teamMembers, TeamMemberDto.class);
    }

    @Override
    public Optional<TeamMemberDto> findById(TeamMemberId id) {
        Optional<TeamMember> teamMember = teamMemberRepository.findById(id);
        return teamMember.map(m -> mapper.map(m, TeamMemberDto.class));
    }

    @Override
    public void remove(TeamMemberDto teamMemberDto) {
        TeamMember teamMember = mapper.map(teamMemberDto, TeamMember.class);
        teamMemberRepository.delete(teamMember);
    }

    @Override
    public TeamMemberDto save(TeamMemberDto teamMemberDto) {
        TeamMember teamMember = mapper.map(teamMemberDto, TeamMember.class);
        teamMember = teamMemberRepository.save(teamMember);
        return mapper.map(teamMember, TeamMemberDto.class);
    }

    @Override
    public List<TeamMemberDto> findCurrentlyValid(List<TeamMemberDto> teamDtos) {
        return teamDtos.stream().filter(
                m -> DateTimeUtils.isCurrentlyValid(m.getTeamMemberId().getApplyBeginDate(), m.getApplyEndDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MemberDto> findTeamLeaderOrManager(List<TeamMemberDto> teamMemberDtos) {
        Optional<Member> member = teamMemberDtos.stream()
                .filter(m -> m.getTeamLeadFlag().equals(FlagOption.Y) || m.getTeamManagerFlag().equals(FlagOption.Y))
                .map(m -> m.getTeamMemberId().getMember()).findFirst();
        return member.map(m -> mapper.map(m, MemberDto.class));
    }

    @Override
    public List<TeamDto> findActiveTeamsOfMember(MemberDto memberDto) {
        List<TeamMember> teamMembers = teamMemberRepository
                .findAll(teamMemberQuery.findByMemberSeq(memberDto.getMemberSeq())
                        .and(teamMemberQuery.findActiveTeamOnly()).and(teamMemberQuery.findCurrentlyValid()));
        List<Team> teams = teamMembers.stream().map(m -> m.getTeamMemberId().getTeam()).collect(Collectors.toList());
        return mapper.mapAsList(teams, TeamDto.class);
    }

    @Override
    public List<TeamDto> addLeaderToTeamList(List<TeamDto> teams) {
        List<TeamMember> teamMembers = new ArrayList<>(teamMemberRepository.findAll(teamMemberQuery.hasLeaderOrManager()
                .and(teamMemberQuery.findActiveTeamOnly()).and(teamMemberQuery.findCurrentlyValid())));
        for (TeamDto team : teams) {
            Integer teamSeq = team.getTeamSeq();
            Optional<Member> leaderOrManager = teamMembers.stream()
                    .filter(m -> m.getTeamMemberId().getTeam().getTeamSeq().equals(teamSeq)).findFirst()
                    .map(m -> m.getTeamMemberId().getMember());
            team.setLeaderOrManager(leaderOrManager.map(m -> mapper.map(m, MemberDto.class)).orElse(null));
        }
        return teams;
    }

    @Override
    public Page<TeamWithMembersAndLeaderDto> addMembersAndLeader(Page<Team> teams) {
        List<TeamMember> teamMembers = teamMemberRepository.findAll(teamMemberQuery.findActiveMemberOnly()
                .and(teamMemberQuery.findActiveTeamOnly()).and(teamMemberQuery.findCurrentlyValid()));
        Page<TeamWithMembersAndLeaderDto> result = teams.map(team -> {
            TeamWithMembersAndLeaderDto item = mapper.map(team, TeamWithMembersAndLeaderDto.class);
            List<Member> members = teamMembers.stream()
                    .filter(m -> m.getTeamMemberId().getTeam().getTeamSeq().equals(team.getTeamSeq()))
                    .map(m -> m.getTeamMemberId().getMember()).distinct().collect(Collectors.toList());
            item.setMembers(mapper.mapAsList(members, MemberDto.class));
            Optional<Member> leaderOrManager = teamMembers.stream()
                    .filter(m -> m.getTeamMemberId().getTeam().getTeamSeq().equals(team.getTeamSeq()))
                    .filter(m -> FlagOption.Y.equals(m.getTeamLeadFlag())
                            || FlagOption.Y.equals(m.getTeamManagerFlag()))
                    .map(m -> m.getTeamMemberId().getMember()).findFirst();
            item.setLeaderOrManager(leaderOrManager.map(m -> mapper.map(m, MemberDto.class)).orElse(null));
            return item;
        });
        return result;
    }

    @Override
    public List<MemberDto> findActiveMembersOfTeam(TeamDto teamDtos) {
        List<TeamMember> teamMembers = teamMemberRepository.findAll(
            teamMemberQuery.findByTeamSeq(teamDtos.getTeamSeq())
                        .and(teamMemberQuery.findCurrentlyValid())
                        .and(teamMemberQuery.findActiveMemberOnly())
        );
        List<MemberDto> currentMembers = mapper.mapAsList(teamMembers.stream().map(m->m.getTeamMemberId().getMember()).collect(Collectors.toList()), MemberDto.class);
        return currentMembers;
    }

    
    @Override
    public List<TeamMemberDto> findSearchBelong(MemberDto memberDto) {
        Member member = mapper.map(memberDto, Member.class);
        List<TeamMember> teamMembers = teamMemberRepository.findAll();
        List<TeamMember> teams = teamMembers.stream().filter(m->m.getTeamMemberId().getMember().getMemberSeq().equals(member.getMemberSeq()))
        .collect(Collectors.toList());
        return mapper.mapAsList(teams, TeamMemberDto.class);
    }

      
    @Override
    public List<TeamMemberDto> findSearchBelongTeam(TeamDto teamDto) {
    	Team team = mapper.map(teamDto, Team.class);
        List<TeamMember> teamMembers = teamMemberRepository.findAll();
        List<TeamMember> teams = teamMembers.stream().filter(m->m.getTeamMemberId().getTeam().getTeamSeq().equals(team.getTeamSeq()))
        								.collect(Collectors.toList());
        return mapper.mapAsList(teams, TeamMemberDto.class);
    }

}
