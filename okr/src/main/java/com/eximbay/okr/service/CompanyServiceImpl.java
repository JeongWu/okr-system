package com.eximbay.okr.service;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.company.CompanyDashboardResponse;
import com.eximbay.okr.dto.company.CompanyDto;
import com.eximbay.okr.dto.evaluationobjective.EvaluationObjectiveDto;
import com.eximbay.okr.dto.feedback.FeedBackForCompanyDashboardDto;
import com.eximbay.okr.dto.keyresultcollaborator.KeyResultCollaboratorDto;
import com.eximbay.okr.dto.like.LikeDto;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.objectiverelation.ObjectiveRelationDto;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.entity.Company;
import com.eximbay.okr.entity.EvaluationOkr;
import com.eximbay.okr.enumeration.ObjectiveType;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.ProgressBarModel;
import com.eximbay.okr.model.TeamForWireframeModel;
import com.eximbay.okr.model.company.CompanyDashboardContentModel;
import com.eximbay.okr.model.company.CompanyOkrModel;
import com.eximbay.okr.model.company.CompanyUpdateFormModel;
import com.eximbay.okr.model.company.EditCompanyModel;
import com.eximbay.okr.model.feedback.FeedbackForViewOkrModel;
import com.eximbay.okr.model.keyResult.KeyResultViewOkrModel;
import com.eximbay.okr.model.objective.ObjectiveViewOkrModel;
import com.eximbay.okr.model.okr.QuarterlyOkrModel;
import com.eximbay.okr.repository.CompanyRepository;
import com.eximbay.okr.service.Interface.ICompanyService;
import com.eximbay.okr.service.Interface.IEvaluationObjectiveService;
import com.eximbay.okr.service.Interface.IEvaluationOkrService;
import com.eximbay.okr.service.Interface.IFeedbackService;
import com.eximbay.okr.service.Interface.IKeyResultCollaboratorService;
import com.eximbay.okr.service.Interface.IKeyResultService;
import com.eximbay.okr.service.Interface.ILikeService;
import com.eximbay.okr.service.Interface.IMemberService;
import com.eximbay.okr.service.Interface.IObjectiveRelationService;
import com.eximbay.okr.service.Interface.IObjectiveService;
import com.eximbay.okr.service.Interface.ITeamMemberService;
import com.eximbay.okr.service.Interface.ITeamService;
import com.eximbay.okr.utils.DateTimeUtils;
import com.eximbay.okr.utils.NumberUtils;
import com.eximbay.okr.utils.StringUtils;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements ICompanyService {

    private final MapperFacade mapper;
    private final CompanyRepository companyRepository;
    private final IObjectiveService objectiveService;
    private final IFeedbackService feedbackService;
    private final IMemberService memberService;
    private final IKeyResultService keyResultService;
    private final IObjectiveRelationService objectiveRelationService;
    private final IKeyResultCollaboratorService keyResultCollaboratorService;
    private final ILikeService likeService;
    private final ITeamMemberService teamMemberService;
    private final ITeamService teamService;
    private final IEvaluationOkrService evaluationOkrService;
    private final IEvaluationObjectiveService evaluationObjectiveService;

    @Override
    public List<CompanyDto> findAll() {
        List<Company> companies = companyRepository.findAll();
        return mapper.mapAsList(companies, CompanyDto.class);
    }

    @Override
    @Transactional
    public Optional<CompanyDto> findById(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        Optional<CompanyDto> companyDto = company.map(m -> mapper.map(m, CompanyDto.class));
        return companyDto;
    }

    @Override
    public void remove(CompanyDto companyDto) {
        Company company = mapper.map(companyDto, Company.class);
        companyRepository.delete(company);
    }

    @Override
    @CacheEvict(value = "company", allEntries = true)
    public CompanyDto save(CompanyDto companyDto) {
        Company company = mapper.map(companyDto, Company.class);
        company = companyRepository.save(company);
        return mapper.map(company, CompanyDto.class);
    }

    @Override
    @Cacheable(value = "company")
    public Optional<CompanyDto> getCompany() {
        return companyRepository.findFirstByOrderByCompanySeq().map(m -> mapper.map(m, CompanyDto.class));
    }

    @Override
    public EditCompanyModel buildEditCompanyModel() {
        Optional<Company> company = companyRepository.findFirstByOrderByCompanySeq();
        if (company.isEmpty()) throw new UserException(new NotFoundException("Do not have any record of Company"));

        EditCompanyModel dataModel = new EditCompanyModel();
        CompanyUpdateFormModel model = mapper.map(company.get(), CompanyUpdateFormModel.class);
        dataModel.setMutedHeader(model.getLocalName());
        model.setGoogleSignIn(company.get().getGoogleSignIn().equals(FlagOption.Y));
        dataModel.setModel(model);
        return dataModel;
    }

    @Override
    @CacheEvict(value = "company", allEntries = true)
    public void updateFormModel(CompanyUpdateFormModel updateFormModel) {
        Optional<Company> company = companyRepository.findFirstByOrderByCompanySeq();
        if (company.isEmpty()) throw new UserException(new NotFoundException("Do not have any record of Company"));

        mapper.map(updateFormModel, company.get());
        if (updateFormModel.isGoogleSignIn()) company.get().setGoogleSignIn(FlagOption.Y);
        else company.get().setGoogleSignIn(FlagOption.N);

        Company saveCompany = companyRepository.save(company.get());
    }

    @Override
    public CompanyDashboardContentModel buildCompanyDashboardContentModel(String quarter) {
        CompanyDashboardContentModel model = new CompanyDashboardContentModel();

        List<String> quarters = objectiveService.findAllQuarters();
        model.setQuarters(quarters);
        if (!quarters.contains(quarter)) {
            model.setCurrentQuarter(DateTimeUtils.findCurrentQuarter());
        } else {
            model.setCurrentQuarter(quarter);
        }

        List<ObjectiveDto> objectives = objectiveService.findAllInQuarter(quarter);
        model.setTeams(getProgressBarForTeams(objectives));
        model.setMembers(getProgressBarForMembers(objectives));

        List<FeedBackForCompanyDashboardDto> feedbacks =
                mapper.mapAsList(feedbackService.findTop10ByOrderByCreatedDateDesc(), FeedBackForCompanyDashboardDto.class);
        List<Integer> feedbacksSeq = feedbacks.stream().map(FeedBackForCompanyDashboardDto::getFeedbackSeq).collect(Collectors.toList());
        List<LikeDto> likes = likeService.getLikeByListFeedbacksSeq(feedbacksSeq);
        mapLikesIntoFeedbackDto(feedbacks, likes);

        model.setFeedbacks(feedbacks);

        return model;
    }

    @Override
    public QuarterlyOkrModel buildQuarterlyOkrModel(String quarter, String type, Integer seq) {
        QuarterlyOkrModel model = new QuarterlyOkrModel();

        List<String> quarters = objectiveService.findAllQuarters();
        if (!quarters.contains(quarter)) {
            model.setCurrentQuarter(DateTimeUtils.findCurrentQuarter());
        } else {
            model.setCurrentQuarter(quarter);
        }

        if (type != null) model.setType(type);
        if (seq != null) model.setSeq(seq);

        List<TeamDto> teams = teamService.findAllInUse();
        List<TeamDto> teamsWithLeaderOrManager = teamMemberService.addLeaderToTeamList(teams);
        List<TeamForWireframeModel> teamForWireframeModels = mapper.mapAsList(teamsWithLeaderOrManager,
                TeamForWireframeModel.class);
        model.setTeams(teamForWireframeModels);

        List<MemberDto> members = memberService.findActiveMembers();
        model.setMembers(members);

        return model;
    }

    private void mapLikesIntoFeedbackDto(List<FeedBackForCompanyDashboardDto> feedbacks, List<LikeDto> likes) {
        Map<Integer, Long> likesCount = likes.stream().collect(Collectors.groupingBy(LikeDto::getSourceSeq, Collectors.counting()));
        for (FeedBackForCompanyDashboardDto feedback : feedbacks) {
            feedback.setLikes(likesCount.getOrDefault(feedback.getFeedbackSeq(), 0L));
        }
    }

    private List<ProgressBarModel> getProgressBarForTeams(List<ObjectiveDto> objectives) {
        Map<TeamDto, List<ObjectiveDto>> map = objectives.stream()
                .filter(m -> m.getTeam() != null && m.getTeam().getUseFlag().equals(FlagOption.Y))
                .collect(Collectors.groupingBy(ObjectiveDto::getTeam, Collectors.toList()));
        List<ProgressBarModel> teams = map.keySet().stream()
                .map(k -> {
                    Double progress = map.get(k).stream().collect(Collectors.summarizingDouble(ObjectiveDto::getProgress)).getAverage();
                    return new ProgressBarModel(k.getLocalName(), NumberUtils.formatDouble(progress, 1), 10.0);
                })
                .sorted(Comparator.comparing(ProgressBarModel::getProgress).reversed())
                .collect(Collectors.toList());
        return teams;
    }

    private List<ProgressBarModel> getProgressBarForMembers(List<ObjectiveDto> objectives) {
        Map<MemberDto, List<ObjectiveDto>> map = objectives.stream()
                .filter(m -> m.getMember() != null && m.getMember().getUseFlag().equals(FlagOption.Y))
                .collect(Collectors.groupingBy(ObjectiveDto::getMember, Collectors.toList()));
        List<ProgressBarModel> members = map.keySet().stream()
                .map(k -> {
                    Double progress = map.get(k).stream().collect(Collectors.summarizingDouble(ObjectiveDto::getProgress)).getAverage();
                    return new ProgressBarModel(k.getLocalName(), NumberUtils.formatDouble(progress, 1), 10.0);
                })
                .sorted(Comparator.comparing(ProgressBarModel::getProgress).reversed())
                .limit(20)
                .collect(Collectors.toList());
        return members;
    }

    @Override
    public CompanyDashboardResponse getCompanyDashboardModel(String quarter) {
        CompanyDashboardResponse result = new CompanyDashboardResponse();
        if (StringUtils.isNullOrEmpty(quarter)) quarter = DateTimeUtils.findCurrentQuarter();

        List<ObjectiveDto> objectives = objectiveService.findAllInQuarter(quarter);
        result.setCompanyObjectives(objectives.stream().filter(m -> m.getCompany() != null).collect(Collectors.toList()));
        return result;
    }

    @Override
    public CompanyOkrModel buildCompanyOkrModel(String quarter) {
        CompanyOkrModel model = new CompanyOkrModel();
        if (StringUtils.isNullOrEmpty(quarter)) quarter = DateTimeUtils.findCurrentQuarter();

        model.setQuarter(quarter);
        Optional<MemberDto> currentMember = memberService.getCurrentMember();
        Optional<EvaluationOkr> evaluationOkr = evaluationOkrService.findByQuarterStringAndObjectiveType(quarter, ObjectiveType.COMPANY.name());
        boolean isCurrentMemberCanEditOkr = currentMember.map(m -> m.getAdminFlag().equals(FlagOption.Y) && m.getEditCompanyOkrFlag().equals(FlagOption.Y)).orElse(false);
        boolean isEditable = isCurrentMemberCanEditOkr
                && evaluationOkr.map(e -> e.getQuarterEndDate() == null ||
                e.getQuarterEndDate().compareToIgnoreCase(DateTimeUtils.getCurrentDateInString()) >= 0).orElse(true);

        model.setEditable(isEditable);

        List<ObjectiveViewOkrModel> objectives = objectiveService.findAllCompanyObjectivesOkrInQuarter(quarter);
        List<Integer> objectivesSeq = objectives.stream().map(ObjectiveViewOkrModel::getObjectiveSeq).collect(Collectors.toList());

        List<KeyResultViewOkrModel> keyResults = keyResultService.findByObjectiveSeqIn(objectivesSeq);
        List<Integer> keyResultsSeq = keyResults.stream().map(KeyResultViewOkrModel::getKeyResultSeq).collect(Collectors.toList());

        List<EvaluationObjectiveDto> evaluationObjectiveDtos = evaluationObjectiveService.findByObjectivesSeqIn(objectivesSeq);
        List<FeedbackForViewOkrModel> feedbacks = feedbackService.getFeedbackForViewOkr(objectivesSeq, keyResultsSeq);
        List<ObjectiveRelationDto> objectiveRelations = objectiveRelationService.findByObjectiveSeqInAndRelatedObjectiveNotNull(objectivesSeq);
        List<KeyResultCollaboratorDto> keyResultCollaborators = keyResultCollaboratorService.findByKeyResultSeqIn(keyResultsSeq);
        List<LikeDto> likes = likeService.getLikeForViewOkr(objectivesSeq, keyResultsSeq);

        objectives.forEach(m -> {
            m.setKeyResults(keyResults.stream().filter(k -> m.getObjectiveSeq().equals(k.getObjective().getObjectiveSeq())).collect(Collectors.toList()));
        });

        objectiveService.mapFeedbackIntoObjectiveModel(objectives, feedbacks);
        objectiveService.mapObjectiveRelationsIntoObjectiveModel(objectives, objectiveRelations);
        objectiveService.mapKeyResultCollaborators(objectives, keyResultCollaborators);
        objectiveService.mapLikesIntoObjectiveModel(objectives, likes);
        objectiveService.mapEditableForObjectiveModel(objectives, evaluationObjectiveDtos, isCurrentMemberCanEditOkr);
        model.setObjectives(objectives);

        return model;
    }
}
