package com.eximbay.okr.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.eximbay.okr.dto.evaluationobjective.EvaluationObjectiveDto;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.dto.teammember.TeamMemberDto;
import com.eximbay.okr.enumeration.ObjectiveType;
import com.eximbay.okr.utils.DateTimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eximbay.okr.config.security.MyUserDetails;
import com.eximbay.okr.constant.ErrorMessages;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.division.DivisionDto;
import com.eximbay.okr.dto.keyresultcollaborator.KeyResultCollaboratorDto;
import com.eximbay.okr.dto.teamhistory.TeamHistoryDto;
import com.eximbay.okr.dto.like.LikeDto;
import com.eximbay.okr.dto.objectiverelation.ObjectiveRelationDto;
import com.eximbay.okr.dto.team.TeamWithMembersAndLeaderDto;
import com.eximbay.okr.entity.*;
import com.eximbay.okr.enumeration.EntityType;
import com.eximbay.okr.enumeration.FileContentType;
import com.eximbay.okr.enumeration.FileType;
import com.eximbay.okr.exception.RestUserException;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.*;
import com.eximbay.okr.model.EditTeamModel;
import com.eximbay.okr.model.feedback.FeedbackForViewOkrModel;
import com.eximbay.okr.model.TeamUpdateFormModel;
import com.eximbay.okr.model.keyResult.KeyResultViewOkrModel;
import com.eximbay.okr.model.objective.ObjectiveViewOkrModel;
import com.eximbay.okr.model.team.TeamAddModel;
import com.eximbay.okr.model.team.TeamViewOkrModel;
import com.eximbay.okr.repository.TeamRepository;
import com.eximbay.okr.service.Interface.*;
import com.eximbay.okr.service.specification.TeamQuery;
import com.eximbay.okr.utils.PagingUtils;
import javassist.NotFoundException;
import lombok.*;
import ma.glasnost.orika.MapperFacade;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements ITeamService {

    private final MapperFacade mapper;
    private final TeamRepository teamRepository;
    private final ITeamMemberService teamMemberService;
    private final IObjectiveService objectiveService;
    private final IKeyResultService keyResultService;
    private final TeamQuery teamQuery;
    private final IFeedbackService feedbackService;
    private final ILikeService likeService;
    private final IObjectiveRelationService objectiveRelationService;
    private final IKeyResultCollaboratorService keyResultCollaboratorService;
    private final ITeamHistoryService teamHistoryService;
    private final FileUploadService fileUploadService;
    private final IMemberService memberService;
    private final IEvaluationOkrService evaluationOkrService;
    private final IEvaluationObjectiveService evaluationObjectiveService;

    @Override
    public List<TeamDto> findAll() {
        List<Team> teams = teamRepository.findAll();
        return mapper.mapAsList(teams, TeamDto.class);
    }

    @Override
    public Optional<TeamDto> findById(Integer id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.map(m -> mapper.map(m, TeamDto.class));
    }

    @Override
    public void remove(TeamDto teamDto) {
        Team team = mapper.map(teamDto, Team.class);
        teamRepository.delete(team);
    }

    @Override
    public TeamDto save(TeamDto teamDto) {
        Team team = mapper.map(teamDto, Team.class);
        team = teamRepository.save(team);
        return mapper.map(team, TeamDto.class);
    }

    @Override
    public List<TeamDto> findAllInUse() {
        List<Team> teams = teamRepository.findByUseFlag(FlagOption.Y);
        return mapper.mapAsList(teams, TeamDto.class);
    }

    @Override
    public AllDetailsTeamModel buildAllDetailsTeamModel(Pageable pageable) {
        AllDetailsTeamModel allDetailsTeamModel = new AllDetailsTeamModel();
        Page<Team> teamPage = teamRepository.findAll(teamQuery.findInUse(), PagingUtils.buildPageRequest(pageable));// .toList();
        Page<TeamWithMembersAndLeaderDto> teams = teamMemberService.addMembersAndLeader(teamPage);
        Page<TeamForAllDetailsTeamModel> teamDtoPage = teams.map(team -> {
            TeamForAllDetailsTeamModel teamForAllDetailsTeamModel = mapper.map(team, TeamForAllDetailsTeamModel.class);
            if (teamForAllDetailsTeamModel.getObjectives() != null)
                teamForAllDetailsTeamModel.setObjectives(teamForAllDetailsTeamModel.getObjectives().stream()
                        .filter(m -> FlagOption.N.equals(m.getCloseFlag())).collect(Collectors.toList()));
            return teamForAllDetailsTeamModel;
        });

        allDetailsTeamModel.setTeamsPage(teamDtoPage);
        allDetailsTeamModel.setNavigationPageNumbers(PagingUtils.createNavigationPageNumbers(
                allDetailsTeamModel.getTeamsPage().getNumber(), allDetailsTeamModel.getTeamsPage().getTotalPages()));
        allDetailsTeamModel.setSubheader("Teams");
        allDetailsTeamModel.setMutedHeader(teamDtoPage.getTotalElements() + " Total");
        return allDetailsTeamModel;
    }

    @Override
    public Optional<MemberDto> getCurrentLoginUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(user instanceof MyUserDetails))
            return Optional.empty();
        return Optional.ofNullable(((MyUserDetails) user).getMemberDto());
    }

    @Override
    public boolean isCurrentMemberTeamLeadOrManager(List<TeamMemberDto> teamMemberDtos) {
        Optional<MemberDto> currentUser = getCurrentLoginUser();
        if (currentUser.isEmpty())
            return false;
        return teamMemberDtos.stream()
                .filter(m -> m.getTeamMemberId().getMember().getMemberSeq().equals(currentUser.get().getMemberSeq()))
                .map(m -> m.getTeamLeadFlag().equals(FlagOption.Y) || m.getTeamManagerFlag().equals(FlagOption.Y))
                .findFirst().orElse(false);
    }

    @Override
    public int countByUseFlag(String useFlag) {
        return teamRepository.countByUseFlag(useFlag);
    }

    @Override
    public List<TeamListTableModel> buildListTableModel() {
        List<Team> teams = teamRepository.findAll();

        List<TeamListTableModel> teamListModels = mapper.mapAsList(teams, TeamListTableModel.class);

        for (int i = 0; i < teamListModels.size(); i++) {
            List<TeamMemberDto> teamMemberDtos = mapper.mapAsList(teams.get(i).getTeamMembers(), TeamMemberDto.class);
            List<TeamMemberDto> availableTeamMembers = teamMemberService.findCurrentlyValid(teamMemberDtos);

            Optional<MemberDto> leaderOrManager = teamMemberService.findTeamLeaderOrManager(availableTeamMembers);
            teamListModels.get(i).setLeaderOrManager(leaderOrManager.orElse(null));

            List<Member> members = availableTeamMembers.stream().map(m -> m.getTeamMemberId().getMember()).distinct()
                    .collect(Collectors.toList());
            teamListModels.get(i).setMembers(mapper.mapAsList(members, MemberDto.class));

            DivisionDto divisionDto = mapper.map(teamListModels.get(i).getDivision(), DivisionDto.class);
            teamListModels.get(i).setDivisionName(divisionDto.getLocalName());
        }
        return teamListModels;

    }

    @Override
    public long countAllTeam() {
        return teamRepository.count();
    }

    @Override
    public TeamViewOkrModel buildTeamOkrModel(Integer teamSeq, String quarter) {
        TeamViewOkrModel model = new TeamViewOkrModel();
        TeamDto team = mapper.map(teamRepository.findById(teamSeq)
                .orElseThrow(() -> new UserException(ErrorMessages.resourceNotFound + teamSeq)), TeamDto.class);
        model.setTeam(team);
        model.setMutedHeader(team.getLocalName());
        model.setQuarter(quarter);

        Optional<MemberDto> currentMember = memberService.getCurrentMember();
        Optional<EvaluationOkr> evaluationOkr = evaluationOkrService.findByQuarterStringAndObjectiveType(quarter, ObjectiveType.TEAM.name());
        boolean isCurrentMemberCanEditTeamOkr = currentMember.map(m-> m.getAdminFlag().equals(FlagOption.Y)).orElse(false)
                && teamMemberService.isCurrentMemberCanEditTeamOkr(teamSeq);
        boolean isEditable = isCurrentMemberCanEditTeamOkr && evaluationOkr.map(e -> e.getQuarterEndDate() == null ||
                e.getQuarterEndDate().compareToIgnoreCase(DateTimeUtils.getCurrentDateInString()) >= 0).orElse(true);
        model.setEditable(isEditable);

        List<ObjectiveViewOkrModel> objectives = objectiveService.findTeamObjectivesOkrInQuarterByTeamSeq(teamSeq,
                quarter);
        List<Integer> objectivesSeq = objectives.stream().map(ObjectiveViewOkrModel::getObjectiveSeq)
                .collect(Collectors.toList());

        List<KeyResultViewOkrModel> keyResults = keyResultService.findByObjectiveSeqIn(objectivesSeq);
        List<Integer> keyResultsSeq = keyResults.stream().map(KeyResultViewOkrModel::getKeyResultSeq)
                .collect(Collectors.toList());

        List<EvaluationObjectiveDto> evaluationObjectiveDtos = evaluationObjectiveService.findByObjectivesSeqIn(objectivesSeq);
        List<FeedbackForViewOkrModel> feedbacks = feedbackService.getFeedbackForViewOkr(objectivesSeq, keyResultsSeq);
        List<ObjectiveRelationDto> objectiveRelations = objectiveRelationService
                .findByObjectiveSeqInAndRelatedObjectiveNotNull(objectivesSeq);
        List<KeyResultCollaboratorDto> keyResultCollaborators = keyResultCollaboratorService
                .findByKeyResultSeqIn(keyResultsSeq);
        List<LikeDto> likes = likeService.getLikeForViewOkr(objectivesSeq, keyResultsSeq);

        objectives.forEach(m -> {
            m.setKeyResults(
                    keyResults.stream().filter(k -> m.getObjectiveSeq().equals(k.getObjective().getObjectiveSeq()))
                            .collect(Collectors.toList()));
        });

        objectiveService.mapFeedbackIntoObjectiveModel(objectives, feedbacks);
        objectiveService.mapLikesIntoObjectiveModel(objectives, likes);
        objectiveService.mapObjectiveRelationsIntoObjectiveModel(objectives, objectiveRelations);
        objectiveService.mapKeyResultCollaborators(objectives, keyResultCollaborators);
        objectiveService.mapEditableForObjectiveModel(objectives, evaluationObjectiveDtos, isCurrentMemberCanEditTeamOkr);

        model.setObjectives(objectives);
        return model;
    }

    @Override
    public EditTeamModel buildEditTeamModel(Integer id) {
        EditTeamModel dataModel = new EditTeamModel();
        dataModel.setSubheader("Edit ");
        Optional<Team> team = teamRepository.findById(id);
        Optional<TeamDto> teamDto = findById(id);
        Optional<TeamUpdateFormModel> model = teamDto.map(m -> mapper.map(m, TeamUpdateFormModel.class));
        if (model.isEmpty())
            throw new UserException(new NotFoundException("Not found Object with Id = " + id));
        dataModel.setMutedHeader(model.get().getName());

        model.get().setUseFlag(teamDto.get().getUseFlag().equals(FlagOption.Y));
        model.get().setDivisionDto(teamDto.get().getDivision());
        dataModel.setModel(model.get());
        System.out.println("dataModel!!!: " + dataModel);
        return dataModel;
    }

    @Override
    @Transactional
    public void updateFormModel(TeamUpdateFormModel updateFormModel) {
        Optional<TeamDto> teamDto = findById(updateFormModel.getTeamSeq());
        if (teamDto.isEmpty())
            throw new UserException(
                    new NotFoundException("Not found Object with Id = " + updateFormModel.getTeamSeq()));
        mapper.map(updateFormModel, teamDto.get());

        if (updateFormModel.isUseFlag())
            teamDto.get().setUseFlag(FlagOption.Y);
        else
            teamDto.get().setUseFlag(FlagOption.N);

        teamDto.get().getDivision().setDivisionSeq(updateFormModel.getDivisionDto().getDivisionSeq());

        if (updateFormModel.getImageFile() != null && !updateFormModel.getImageFile().isEmpty()) {
            String imageSrc;
            try {
                imageSrc = fileUploadService.store(FileType.IMAGE, FileContentType.AVATAR, EntityType.TEAM,
                updateFormModel.getImageFile());
            } catch (UserException e) {
                String message = Optional.ofNullable(e.getCause()).orElse(e).getMessage();
                throw new RestUserException(message);
            }
            teamDto.get().setImage(imageSrc);
        }

        TeamHistoryDto teamHistoryDto = mapper.map(teamDto.get(), TeamHistoryDto.class);
        teamHistoryDto.setJustification(updateFormModel.getJustification());
        teamHistoryDto.setTeam(mapper.map(teamDto.get(), TeamDto.class));
        teamHistoryService.save(teamHistoryDto);
        save(teamDto.get());
    }

    @Override
    public Team addTeam(TeamAddModel teamAddModel) {
        Team team = mapper.map(teamAddModel, Team.class);

        if (teamAddModel.isUseFlag()) {
            team.setUseFlag("Y");
        } else {
            team.setUseFlag("N");
        }

        if (teamAddModel.getImageFile() != null && !teamAddModel.getImageFile().isEmpty()) {
            String imageSrc;
            try {
                imageSrc = fileUploadService.store(FileType.IMAGE, FileContentType.AVATAR, EntityType.TEAM,
                        teamAddModel.getImageFile());
            } catch (UserException e) {
                String message = Optional.ofNullable(e.getCause()).orElse(e).getMessage();
                throw new RestUserException(message);
            }
            team.setImage(imageSrc);
        }

        team = teamRepository.save(team);
        // insert new history when adding a team
        TeamHistoryDto teamHistoryDto = mapper.map(team, TeamHistoryDto.class);
        teamHistoryDto.setJustification("Team Added");
        teamHistoryDto.setTeam(mapper.map(team, TeamDto.class));
        teamHistoryDto.setUpdatedDate(Instant.now());
        teamHistoryService.save(teamHistoryDto);

        return team;
    }

    @Override
    public EditForViewAllTeamsModel buildEditAllTeamsModel(Integer id) {
        EditForViewAllTeamsModel dataModel = new EditForViewAllTeamsModel();
        dataModel.setSubheader("Edit Team");
        Optional<Team> team = teamRepository.findById(id);
        Optional<AllTeamUpdateModel> model = team.map(m -> mapper.map(m, AllTeamUpdateModel.class));
        if (model.isEmpty())
            throw new UserException(new NotFoundException("Not found Object with Id = " + id));
        dataModel.setModel(model.get());
        return dataModel;
    }

    @Override
    public void updateForViewAllTeamModel(AllTeamUpdateModel allTeamUpdateModel) {
        Optional<Team> team = teamRepository.findById(allTeamUpdateModel.getTeamSeq());
        if (team.isEmpty())
            throw new UserException(
                    new NotFoundException("Not found Object with Id = " + allTeamUpdateModel.getTeamSeq()));

        if (allTeamUpdateModel.getImageFile() != null && !allTeamUpdateModel.getImageFile().isEmpty()) {
            String imageSrc;
            try {
                imageSrc = fileUploadService.store(FileType.IMAGE, FileContentType.AVATAR, EntityType.TEAM,
                        allTeamUpdateModel.getImageFile());
            } catch (UserException e) {
                String message = Optional.ofNullable(e.getCause()).orElse(e).getMessage();
                throw new RestUserException(message);
            }
            team.get().setImage(imageSrc);
        }

        team.get().setIntroduction(allTeamUpdateModel.getIntroduction());

        teamRepository.save(team.get());
        allTeamUpdateModel.setImageFile(null);

    }
}