package com.eximbay.okr.service;

import com.eximbay.okr.constant.*;
import com.eximbay.okr.dto.*;
import com.eximbay.okr.dto.keyResultCollaborator.KeyResultCollaboratorDto;
import com.eximbay.okr.dto.objectiveRelation.ObjectiveRelationDto;
import com.eximbay.okr.entity.*;
import com.eximbay.okr.enumeration.SourceTable;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.ProgressBarModel;
import com.eximbay.okr.model.company.CompanyDashboardModel;
import com.eximbay.okr.model.company.CompanyOkrModel;
import com.eximbay.okr.model.company.CompanyUpdateFormModel;
import com.eximbay.okr.model.company.EditCompanyModel;
import com.eximbay.okr.model.feedback.FeedbackForCompanyViewOkrModel;
import com.eximbay.okr.model.keyResult.KeyResultCompanyOkrModel;
import com.eximbay.okr.model.keyResultCollaborator.KeyResultCollaboratorForCompanyOkrModel;
import com.eximbay.okr.model.objective.CompanyObjectiveOkrModel;
import com.eximbay.okr.model.objectiveRelation.ObjectiveRelationForCompanyOkrModel;
import com.eximbay.okr.repository.*;
import com.eximbay.okr.service.Interface.*;
import com.eximbay.okr.utils.DateTimeUtils;
import com.eximbay.okr.utils.NumberUtils;
import com.eximbay.okr.utils.StringUtils;
import javassist.NotFoundException;
import lombok.*;
import ma.glasnost.orika.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CompanyServiceImpl implements ICompanyService {
    private final CompanyRepository companyRepository;
    private final IObjectiveService objectiveService;
    private final IFeedbackService feedbackService;
    private final IMemberService memberService;
    private final IKeyResultService keyResultService;
    private final IObjectiveRelationService objectiveRelationService;
    private final IKeyResultCollaboratorService keyResultCollaboratorService;
    private final MapperFacade mapper;

    @Override
    public List<CompanyDto> findAll() {
        List<Company> companies = companyRepository.findAll();
        return mapper.mapAsList(companies, CompanyDto.class);
    }

    @Override
    @Transactional
    public Optional<CompanyDto> findById(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        Optional<CompanyDto> companyDto = company.map(m-> mapper.map(m, CompanyDto.class));
        return companyDto;
    }

    @Override
    public void remove(CompanyDto companyDto) {
        Company company = mapper.map(companyDto, Company.class);
        companyRepository.delete(company);
    }

    @Override
    public CompanyDto save(CompanyDto companyDto) {
        Company company = mapper.map(companyDto, Company.class);
        company = companyRepository.save(company);
        return mapper.map(company, CompanyDto.class);
    }

    @Override
    public Optional<CompanyDto> getCompany() {
        return companyRepository.findFirstByOrderByCompanySeq().map(m-> mapper.map(m, CompanyDto.class));
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
    public void updateFormModel(CompanyUpdateFormModel updateFormModel) {
        Optional<Company> company = companyRepository.findFirstByOrderByCompanySeq();
        if (company.isEmpty()) throw new UserException(new NotFoundException("Do not have any record of Company"));

        mapper.map(updateFormModel, company.get());
        if (updateFormModel.isGoogleSignIn()) company.get().setGoogleSignIn(FlagOption.Y);
        else company.get().setGoogleSignIn(FlagOption.N);

        Company saveCompany = companyRepository.save(company.get());
    }

    @Override
    public CompanyDashboardModel buildCompanyDashboardModel(String quarter) {
        if (StringUtils.isNullOrEmpty(quarter)) quarter = DateTimeUtils.findCurrentQuarter();

        CompanyDashboardModel model = new CompanyDashboardModel();
        List<String> quarters = objectiveService.findAllQuarters();

        if (!quarters.contains(quarter)){
            model.setCurrentQuarter(DateTimeUtils.findCurrentQuarter());
        } else {
            model.setCurrentQuarter(quarter);
        }
        if (!quarters.contains(DateTimeUtils.findCurrentQuarter())) quarters.add(DateTimeUtils.findCurrentQuarter());
        model.setQuarters(quarters);

        Optional<MemberDto> currentMember = memberService.getCurrentMember();
        model.setAddable(currentMember.map(m->m.getAdminFlag().equals(FlagOption.Y)).orElse(false));

        List<ObjectiveDto> objectives = objectiveService.findAllInQuarter(quarter);
        model.setTeams(getProgressBarForTeams(objectives));
        model.setMembers(getProgressBarForMembers(objectives));

        model.setFeedbacks(feedbackService.findTop10ByOrderByCreatedDateDesc());

        return model;
    }

    private List<ProgressBarModel> getProgressBarForTeams(List<ObjectiveDto> objectives){
        Map<TeamDto, List<ObjectiveDto>> map = objectives.stream()
                .filter(m->m.getTeam() != null && m.getTeam().getUseFlag().equals(FlagOption.Y))
                .collect(Collectors.groupingBy(ObjectiveDto::getTeam, Collectors.toList()));
        List<ProgressBarModel> teams = map.keySet().stream()
                .map(k -> {
                    Double progress = map.get(k).stream().collect(Collectors.summarizingDouble(ObjectiveDto::getProgress)).getAverage();
                    return new ProgressBarModel(k.getLocalName(), NumberUtils.formatDouble(progress,1), 10.0);
                })
                .sorted(Comparator.comparing(ProgressBarModel::getProgress).reversed())
                .collect(Collectors.toList());
        return teams;
    }

    private List<ProgressBarModel> getProgressBarForMembers(List<ObjectiveDto> objectives){
        Map<MemberDto, List<ObjectiveDto>> map = objectives.stream()
                .filter(m->m.getMember() != null && m.getMember().getUseFlag().equals(FlagOption.Y))
                .collect(Collectors.groupingBy(ObjectiveDto::getMember, Collectors.toList()));
        List<ProgressBarModel> members = map.keySet().stream()
                .map(k -> {
                    Double progress = map.get(k).stream().collect(Collectors.summarizingDouble(ObjectiveDto::getProgress)).getAverage();
                    return new ProgressBarModel(k.getLocalName(), NumberUtils.formatDouble(progress,1), 10.0);
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
        result.setCompanyObjectives(objectives.stream().filter(m->m.getCompany() != null).collect(Collectors.toList()));
        return result;
    }

    @Override
    public CompanyOkrModel buildCompanyOkrModel(String quarter) {
        CompanyOkrModel model = new CompanyOkrModel();
        if (StringUtils.isNullOrEmpty(quarter)) quarter = DateTimeUtils.findCurrentQuarter();

        model.setQuarter(quarter);
        model.setEditable(memberService.getCurrentMember().map(m-> m.getAdminFlag().equals(FlagOption.Y)).orElse(false));

        List<CompanyObjectiveOkrModel> objectives = objectiveService.findAllCompanyObjectiveOkrInQuarter(quarter);
        List<Integer> objectivesSeq = objectives.stream().map(CompanyObjectiveOkrModel::getObjectiveSeq).collect(Collectors.toList());

        List<KeyResultCompanyOkrModel> keyResults = keyResultService.findByObjectiveSeqIn(objectivesSeq);
        List<Integer> keyResultsSeq = keyResults.stream().map(KeyResultCompanyOkrModel::getKeyResultSeq).collect(Collectors.toList());

        List<FeedbackForCompanyViewOkrModel> feedbacks = getFeedback(objectivesSeq, keyResultsSeq);
        List<ObjectiveRelationDto> objectiveRelations = getObjectiveRelation(objectivesSeq);
        List<KeyResultCollaboratorDto> keyResultCollaborators = getKeyResultCollaborators(keyResultsSeq);

        objectives.forEach(m-> {
            m.setKeyResults(keyResults.stream().filter(k-> m.getObjectiveSeq().equals(k.getObjective().getObjectiveSeq())).collect(Collectors.toList()));
        });

        mapFeedbackIntoObjectiveModel(objectives, feedbacks);
        mapObjectiveRelationsIntoObjectiveModel(objectives, objectiveRelations);
        mapKeyResultCollaborators(objectives, keyResultCollaborators);
        model.setObjectives(objectives);

        return model;
    }

    private List<FeedbackForCompanyViewOkrModel> getFeedback(List<Integer> objectivesSeq, List<Integer> keyResultsSeq){
        return feedbackService.getFeedbackForCompanyViewOkr(objectivesSeq, keyResultsSeq);
    }

    private List<ObjectiveRelationDto> getObjectiveRelation(List<Integer> objectivesSeq){
        return objectiveRelationService.findByObjectiveSeqInAndRelatedObjectiveNotNull(objectivesSeq);
    }

    private List<KeyResultCollaboratorDto> getKeyResultCollaborators(List<Integer> keyResultsSeq){
        return keyResultCollaboratorService.findByKeyResultSeqIn(keyResultsSeq);
    }

    private void mapFeedbackIntoObjectiveModel(List<CompanyObjectiveOkrModel> objectives, List<FeedbackForCompanyViewOkrModel> feedbacks){
        Map<String, List<FeedbackForCompanyViewOkrModel>> feedbackMap = feedbacks.stream()
                            .collect(Collectors.groupingBy(FeedbackForCompanyViewOkrModel::getSourceTable));

        Map<Integer, Long> objectiveMap = feedbackMap.getOrDefault(SourceTable.OBJECTIVE.name(),new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(FeedbackForCompanyViewOkrModel::getSourceSeq, Collectors.counting()));

        Map<Integer, Long> keyResultMap = feedbackMap.getOrDefault(SourceTable.KEY_RESULT.name(), new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(FeedbackForCompanyViewOkrModel::getSourceSeq, Collectors.counting()));

        objectives.forEach(o-> {
            o.setFeedbackCount(Optional.ofNullable(objectiveMap.get(o.getObjectiveSeq())).orElse(0L));
            o.getKeyResults().forEach(k -> {
                k.setFeedbackCount(Optional.ofNullable(keyResultMap.get(k.getKeyResultSeq())).orElse(0L));
            });
        });
    }

    private void mapObjectiveRelationsIntoObjectiveModel(List<CompanyObjectiveOkrModel> objectives, List<ObjectiveRelationDto> objectiveRelations){
        objectives.forEach(o->{
            o.setRelatedObjectives(
                    Optional.ofNullable(objectiveRelations).orElse(new ArrayList<>())
                    .stream()
                    .filter(m-> m.getObjective().getObjectiveSeq().equals(o.getObjectiveSeq()))
                    .filter(m-> (m.getRelatedObjective().getTeam() != null && m.getRelatedObjective().getTeam().getUseFlag().equals(FlagOption.Y))
                            || (m.getRelatedObjective().getMember() != null && m.getRelatedObjective().getMember().getUseFlag().equals(FlagOption.Y)))
                    .map( e -> {
                        ObjectiveRelationForCompanyOkrModel model = new ObjectiveRelationForCompanyOkrModel();
                        mapRelatedObjectiveInfo(model,e);
                        return model;
                    }).distinct().collect(Collectors.toList())
            );
        });
    }

    private void mapRelatedObjectiveInfo(ObjectiveRelationForCompanyOkrModel model, ObjectiveRelationDto objectiveRelationDto){
        ObjectiveDto relatedObjective = objectiveRelationDto.getRelatedObjective();
        model.setObjectiveType(relatedObjective.getObjectiveType());
        if (relatedObjective.getTeam() != null) {
            model.setObjectSeq(relatedObjective.getTeam().getTeamSeq());
            model.setObjectName(relatedObjective.getTeam().getLocalName());
            model.setImage(relatedObjective.getTeam().getImage());
        } else if (relatedObjective.getMember() != null){
            model.setObjectSeq(relatedObjective.getMember().getMemberSeq());
            model.setObjectName(relatedObjective.getMember().getLocalName());
            model.setImage(relatedObjective.getMember().getImage());
        }
    }

    private void mapKeyResultCollaborators(List<CompanyObjectiveOkrModel> objectives, List<KeyResultCollaboratorDto> keyResultCollaborators){
        Map<Integer, List<KeyResultCollaboratorDto>> map = keyResultCollaborators.stream()
                .collect(Collectors.groupingBy(e->e.getKeyResult().getKeyResultSeq(), Collectors.toList()));

        objectives.forEach(o->{
            o.getKeyResults().forEach(k->{
                k.setKeyResultCollaborators(
                        map.getOrDefault(k.getKeyResultSeq(), new ArrayList<>()).stream()
                                .filter(e-> e.getCollaborator().getUseFlag().equals(FlagOption.Y))
                                .map(e->
                                    KeyResultCollaboratorForCompanyOkrModel.builder()
                                        .objectSeq(e.getCollaborator().getMemberSeq())
                                        .objectName(e.getCollaborator().getLocalName())
                                        .image(e.getCollaborator().getImage())
                                        .build()
                        ).distinct().collect(Collectors.toList())
                );
            });
        });
    }
}
