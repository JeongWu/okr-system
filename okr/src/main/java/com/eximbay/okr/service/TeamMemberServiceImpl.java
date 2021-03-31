package com.eximbay.okr.service;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.division.DivisionDto;
import com.eximbay.okr.dto.feedback.FeedbackDto;
import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.member.MemberWithActiveDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.dto.team.TeamWithMembersAndLeaderDto;
import com.eximbay.okr.dto.teammember.TeamMemberDto;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.entity.TeamMember;
import com.eximbay.okr.entity.id.TeamMemberId;
import com.eximbay.okr.model.TeamListTableModel;
import com.eximbay.okr.repository.TeamMemberRepository;
import com.eximbay.okr.service.Interface.IFeedbackService;
import com.eximbay.okr.service.Interface.IGenericQuery;
import com.eximbay.okr.service.Interface.IKeyResultService;
import com.eximbay.okr.service.Interface.IMemberService;
import com.eximbay.okr.service.Interface.ITeamMemberService;
import com.eximbay.okr.service.specification.TeamMemberQuery;
import com.eximbay.okr.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamMemberServiceImpl implements ITeamMemberService, IGenericQuery<TeamMember> {

    private final MapperFacade mapper;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberQuery teamMemberQuery;
    private final IMemberService memberService;
    private final ObjectiveServiceImpl objectiveService;
    private final IKeyResultService keyResultService;
    private final IFeedbackService feedbackService;

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
    public Optional<MemberDto> findTeamLeaderOrManager(Integer teamSeq) {
        List<TeamMember> teamMembers = teamMemberRepository.findAll(
                teamMemberQuery.findTeamLeaderOrManager(teamSeq)
        );
        Optional<Member> leaderOrManager = teamMembers.stream().map(m -> m.getTeamMemberId().getMember()).findFirst();
        return leaderOrManager.map(m -> mapper.map(m, MemberDto.class));
    }

    @Override
    public boolean isCurrentMemberLeaderOrManager(Integer teamSeq) {
        Optional<MemberDto> currentMember = memberService.getCurrentMember();
        if (currentMember.isEmpty()) return false;
        Optional<MemberDto> leaderOrManager = findTeamLeaderOrManager(teamSeq);
        return leaderOrManager.map(e -> e.getMemberSeq().equals(currentMember.get().getMemberSeq())).orElse(false);
    }

    @Override
    public boolean isCurrentMemberCanEditTeamOkr(Integer teamSeq) {
        Optional<MemberDto> currentMember = memberService.getCurrentMember();
        if (currentMember.isEmpty()) return false;
        List<TeamMember> teamMembers = teamMemberRepository.findAll(
                teamMemberQuery.isCurrentMemberCanEditTeamOkr(teamSeq, currentMember.get().getMemberSeq())
        );
        return teamMembers.size() > 0;
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
    public List<TeamListTableModel> addMembersAndLeaderForDataTable(List<TeamDto> teams) {

        List<TeamMember> teamMembers = teamMemberRepository.findAll(teamMemberQuery.findActiveMemberOnly()
                .and(teamMemberQuery.findActiveTeamOnly()).and(teamMemberQuery.findCurrentlyValid()));

        List<TeamListTableModel> teamDtos = mapper.mapAsList(teams, TeamListTableModel.class);

        for (TeamListTableModel team : teamDtos) {
            Integer teamSeq = team.getTeamSeq();
            Optional<Member> leaderOrManager = teamMembers.stream()
                    .filter(m -> m.getTeamMemberId().getTeam().getTeamSeq().equals(team.getTeamSeq()))
                    .filter(m -> FlagOption.Y.equals(m.getTeamLeadFlag())
                            || FlagOption.Y.equals(m.getTeamManagerFlag()))
                    .map(m -> m.getTeamMemberId().getMember()).findFirst();
            team.setLeaderOrManager(leaderOrManager.map(m -> mapper.map(m, MemberDto.class)).orElse(null));

            List<Member> members = teamMembers.stream()
                    .filter(m -> m.getTeamMemberId().getTeam().getTeamSeq().equals(teamSeq))
                    .map(m -> m.getTeamMemberId().getMember()).distinct().collect(Collectors.toList());
            team.setMembers(mapper.mapAsList(members, MemberDto.class));

            DivisionDto divisionDto = mapper.map(team.getDivision(), DivisionDto.class);
            team.setDivisionName(divisionDto.getName());

        }
        return teamDtos;
    }


    @Override
    public List<TeamMemberDto> findSearchBelong(MemberDto memberDto) {
        Member member = mapper.map(memberDto, Member.class);
        List<TeamMember> teamMembers = teamMemberRepository.findAll();
        List<TeamMember> teams = teamMembers.stream().filter(m -> m.getTeamMemberId().getMember().getMemberSeq().equals(member.getMemberSeq()))
                .collect(Collectors.toList());
        return mapper.mapAsList(teams, TeamMemberDto.class);
    }


    @Override
    public List<TeamMemberDto> findSearchBelongTeam(TeamDto teamDto) {
        Team team = mapper.map(teamDto, Team.class);
        List<TeamMember> teamMembers = teamMemberRepository.findAll();
        List<TeamMember> teams = teamMembers.stream().filter(m -> m.getTeamMemberId().getTeam().getTeamSeq().equals(team.getTeamSeq()))
                .collect(Collectors.toList());
        return mapper.mapAsList(teams, TeamMemberDto.class);
    }

    @Override
    public List<TeamDto> findTeamsByMemberSeq(Integer memberSeq) {
        List<TeamMember> teamMembers = teamMemberRepository.findAll(
                teamMemberQuery.findCurrentlyValid()
                        .and(teamMemberQuery.findActiveTeamOnly())
                        .and(teamMemberQuery.findActiveMemberOnly())
                        .and(teamMemberQuery.findByMemberSeq(memberSeq))
        );
        List<Team> teams = teamMembers.stream().map(e -> e.getTeamMemberId().getTeam()).collect(Collectors.toList());
        return mapper.mapAsList(teams, TeamDto.class);
    }

    @Override
    public Page<MemberWithActiveDto> addActiveMember(Page<Member> members) {
        List<TeamMember> teamMembers = teamMemberRepository.findAll(teamMemberQuery.findActiveMemberOnly());
        Page<MemberWithActiveDto> result = members.map(member -> {
            MemberWithActiveDto item = mapper.map(member, MemberWithActiveDto.class);
            List<Team> teams = teamMembers.stream()
                    .filter(m -> m.getTeamMemberId().getMember().getMemberSeq().equals(member.getMemberSeq()))
                    .map(m -> m.getTeamMemberId().getTeam()).distinct().collect(Collectors.toList());
            List<ObjectiveDto> objectives = objectiveService.findMemberObjective(member.getMemberSeq());
            List<Integer> objectSeq = new ArrayList<>();
            for (ObjectiveDto objects : objectives) {
                objectSeq.add(objects.getObjectiveSeq());
            }
            List<KeyResultDto> keys = keyResultService.findByObjectSeq(objectSeq);
            List<FeedbackDto> feedbacks = feedbackService.findByMember(member);
            item.setTeams(mapper.mapAsList(teams, TeamDto.class));
            item.setObjectives(objectives);
            item.setKeyResults(keys.size());
            item.setFeedbacks(feedbacks.size());
            return item;
        });
        return result;
    }
}
