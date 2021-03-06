package com.eximbay.okr.service;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.evaluationobjective.EvaluationObjectiveDto;
import com.eximbay.okr.dto.keyresultcollaborator.KeyResultCollaboratorDto;
import com.eximbay.okr.dto.like.LikeDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.objective.UpdateObjectivePriorityDto;
import com.eximbay.okr.dto.objective.UpdateObjectivePriorityRequest;
import com.eximbay.okr.dto.objectivehistory.ObjectiveHistoryDto;
import com.eximbay.okr.dto.objectiverelation.ObjectiveRelationDto;
import com.eximbay.okr.entity.KeyResultHistory;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.entity.ObjectiveHistory;
import com.eximbay.okr.entity.Objective_;
import com.eximbay.okr.enumeration.SourceTable;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.feedback.FeedbackForViewOkrModel;
import com.eximbay.okr.model.keyResultCollaborator.KeyResultCollaboratorForCompanyOkrModel;
import com.eximbay.okr.model.objective.ObjectiveViewOkrModel;
import com.eximbay.okr.model.objectiveRelation.ObjectiveRelationViewOkrModel;
import com.eximbay.okr.repository.KeyResultHistoryRepository;
import com.eximbay.okr.repository.KeyResultRepository;
import com.eximbay.okr.repository.ObjectiveHistoryRepository;
import com.eximbay.okr.repository.ObjectiveRepository;
import com.eximbay.okr.service.Interface.IObjectiveHistoryService;
import com.eximbay.okr.service.Interface.IObjectiveService;
import com.eximbay.okr.service.specification.ObjectiveQuery;
import com.eximbay.okr.utils.DateTimeUtils;
import com.eximbay.okr.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ObjectiveServiceImpl implements IObjectiveService {

    private final MapperFacade mapper;
    private final ObjectiveRepository objectiveRepository;
    private final ObjectiveQuery objectiveQuery;
    private final IObjectiveHistoryService objectiveHistoryService;
    private final ObjectiveHistoryRepository objectiveHistoryRepository;
    private final KeyResultRepository keyResultRepository;
    private final KeyResultHistoryRepository keyResultHistoryRepository;

    @Override
    public List<ObjectiveDto> findAll() {
        List<Objective> objectives = objectiveRepository.findAll();
        return mapper.mapAsList(objectives, ObjectiveDto.class);
    }

    @Override
    public Optional<ObjectiveDto> findById(Integer id) {
        Optional<Objective> objective = objectiveRepository.findById(id);
        return objective.map(m -> mapper.map(m, ObjectiveDto.class));
    }

    @Override
    public void remove(ObjectiveDto objectiveDto) {
        Objective objective = mapper.map(objectiveDto, Objective.class);
        objectiveRepository.delete(objective);
    }

    @Override
    public ObjectiveDto save(ObjectiveDto objectiveDto) {
        Objective objective = mapper.map(objectiveDto, Objective.class);
        objective = objectiveRepository.save(objective);
        return mapper.map(objective, ObjectiveDto.class);
    }

    @Override
    public List<ObjectiveDto> findAllInUse() {
        List<Objective> objectives = objectiveRepository.findByCloseFlag(FlagOption.N);
        return mapper.mapAsList(objectives, ObjectiveDto.class);
    }

    @Override
    public List<String> findAllQuarters() {
        return objectiveRepository.findAllQuarters();
    }

    @Override
    public List<ObjectiveDto> findAllInQuarter(String quarterString) {
        try {
            Integer year = Integer.valueOf(quarterString.substring(0, 4));
            Integer quarter = Integer.valueOf(quarterString.substring(quarterString.length() - 2, quarterString.length() - 1));
            List<Objective> objectives = objectiveRepository.findAll(
                    objectiveQuery.findInYear(year)
                            .and(objectiveQuery.findInQuarter(quarter))
            );
            return mapper.mapAsList(objectives, ObjectiveDto.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ObjectiveViewOkrModel> findAllCompanyObjectivesOkrInQuarter(String quarterString) {
        try {
            Integer year = Integer.valueOf(quarterString.substring(0, 4));
            Integer quarter = Integer.valueOf(quarterString.substring(quarterString.length() - 2, quarterString.length() - 1));
            List<Objective> objectives = objectiveRepository.findAll(
                    objectiveQuery.findInYear(year)
                            .and(objectiveQuery.findByCompany())
                            .and(objectiveQuery.findInQuarter(quarter))
                    , Sort.by(Objective_.PRIORITY)
            );
            List<ObjectiveViewOkrModel> results = mapper.mapAsList(objectives, ObjectiveViewOkrModel.class);
            formatObjectiveViewOkrModel(results);
            return results;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void formatObjectiveViewOkrModel(List<ObjectiveViewOkrModel> objectives) {
        objectives.forEach(e -> e.setShortenObjective(StringUtils.shortenString(e.getObjective(), AppConst.KR_MAX_LENGTH)));
    }

    @Override
    public void updateObjectivePriority(UpdateObjectivePriorityRequest request) {
        Map<Integer, Integer> updateValueMap = request.getData().stream()
                .collect(Collectors.toMap(UpdateObjectivePriorityDto::getObjectiveSeq, UpdateObjectivePriorityDto::getPriority));

        List<Objective> objectives = objectiveRepository.findAll(objectiveQuery.findByObjectiveSeqIn(updateValueMap.keySet()));
        List<ObjectiveHistoryDto> objectiveHistories = new ArrayList<>();

        objectives.forEach(o -> {
            o.setPriority(updateValueMap.get(o.getObjectiveSeq()));
            ObjectiveHistoryDto history = mapper.map(o, ObjectiveHistoryDto.class);
            history.setObjectiveObject(mapper.map(o, ObjectiveDto.class));
            history.setJustification(AppConst.DEFAULT_CHANGE_PRIORITY_JUSTIFICATION);
            objectiveHistories.add(history);
        });
        objectiveRepository.saveAll(objectives);
        objectiveHistoryService.saveAll(objectiveHistories);
    }

    @Override
    public List<ObjectiveViewOkrModel> findTeamObjectivesOkrInQuarterByTeamSeq(Integer teamSeq, String quarterString) {
        try {
            Integer year = Integer.valueOf(quarterString.substring(0, 4));
            Integer quarter = Integer.valueOf(quarterString.substring(quarterString.length() - 2, quarterString.length() - 1));
            List<Objective> objectives = objectiveRepository.findAll(
                    objectiveQuery.findInYear(year)
                            .and(objectiveQuery.findInQuarter(quarter))
                            .and(objectiveQuery.isNotNull(Objective_.TEAM))
                            .and(objectiveQuery.findByTeamSeq(teamSeq))
                    , Sort.by(Objective_.PRIORITY)
            );
            List<ObjectiveViewOkrModel> results = mapper.mapAsList(objectives, ObjectiveViewOkrModel.class);
            formatObjectiveViewOkrModel(results);
            return results;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ObjectiveViewOkrModel> findMemberObjectivesOkrInQuarterByMemberSeq(Integer memberSeq, String quarterString) {
        try {
            Integer year = Integer.valueOf(quarterString.substring(0, 4));
            Integer quarter = Integer.valueOf(quarterString.substring(quarterString.length() - 2, quarterString.length() - 1));
            List<Objective> objectives = objectiveRepository.findAll(
                    objectiveQuery.findInYear(year)
                            .and(objectiveQuery.findInQuarter(quarter))
                            .and(objectiveQuery.isNotNull(Objective_.MEMBER))
                            .and(objectiveQuery.findByMemberSeq(memberSeq))
                    , Sort.by(Objective_.PRIORITY)
            );
            List<ObjectiveViewOkrModel> results = mapper.mapAsList(objectives, ObjectiveViewOkrModel.class);
            formatObjectiveViewOkrModel(results);
            return results;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void mapFeedbackIntoObjectiveModel(List<ObjectiveViewOkrModel> objectives, List<FeedbackForViewOkrModel> feedbacks) {
        Map<String, List<FeedbackForViewOkrModel>> feedbackMap = feedbacks.stream()
                .collect(Collectors.groupingBy(FeedbackForViewOkrModel::getSourceTable));

        Map<Integer, Long> objectiveMap = feedbackMap.getOrDefault(SourceTable.OBJECTIVE.name(), new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(FeedbackForViewOkrModel::getSourceSeq, Collectors.counting()));

        Map<Integer, Long> keyResultMap = feedbackMap.getOrDefault(SourceTable.KEY_RESULT.name(), new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(FeedbackForViewOkrModel::getSourceSeq, Collectors.counting()));

        objectives.forEach(o -> {
            o.setFeedbackCount(Optional.ofNullable(objectiveMap.get(o.getObjectiveSeq())).orElse(0L));
            o.getKeyResults().forEach(k -> {
                k.setFeedbackCount(Optional.ofNullable(keyResultMap.get(k.getKeyResultSeq())).orElse(0L));
            });
        });
    }

    @Override
    public void mapLikesIntoObjectiveModel(List<ObjectiveViewOkrModel> objectives, List<LikeDto> likes) {
        Map<String, List<LikeDto>> likeMap = likes.stream()
                .collect(Collectors.groupingBy(LikeDto::getSourceTable));

        Map<Integer, Long> objectiveMap = likeMap.getOrDefault(SourceTable.OBJECTIVE.name(), new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(LikeDto::getSourceSeq, Collectors.counting()));

        Map<Integer, Long> keyResultMap = likeMap.getOrDefault(SourceTable.KEY_RESULT.name(), new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(LikeDto::getSourceSeq, Collectors.counting()));

        objectives.forEach(o -> {
            o.setLikes(Optional.ofNullable(objectiveMap.get(o.getObjectiveSeq())).orElse(0L));
            o.getKeyResults().forEach(k -> {
                k.setLikes(Optional.ofNullable(keyResultMap.get(k.getKeyResultSeq())).orElse(0L));
            });
        });
    }

    @Override
    public void mapObjectiveRelationsIntoObjectiveModel(List<ObjectiveViewOkrModel> objectives, List<ObjectiveRelationDto> objectiveRelations) {
        objectives.forEach(o -> {
            o.setRelatedObjectives(
                    Optional.ofNullable(objectiveRelations).orElse(new ArrayList<>())
                            .stream()
                            .filter(m -> m.getObjective().getObjectiveSeq().equals(o.getObjectiveSeq()))
                            .filter(m -> (m.getRelatedObjective().getTeam() != null && m.getRelatedObjective().getTeam().getUseFlag().equals(FlagOption.Y))
                                    || (m.getRelatedObjective().getMember() != null && m.getRelatedObjective().getMember().getUseFlag().equals(FlagOption.Y)))
                            .map(e -> {
                                ObjectiveRelationViewOkrModel model = new ObjectiveRelationViewOkrModel();
                                mapRelatedObjectiveInfo(model, e);
                                return model;
                            }).distinct().collect(Collectors.toList())
            );
        });
    }

    private void mapRelatedObjectiveInfo(ObjectiveRelationViewOkrModel model, ObjectiveRelationDto objectiveRelationDto) {
        ObjectiveDto relatedObjective = objectiveRelationDto.getRelatedObjective();
        model.setObjectiveType(relatedObjective.getObjectiveType());
        if (relatedObjective.getTeam() != null) {
            model.setObjectSeq(relatedObjective.getTeam().getTeamSeq());
            model.setObjectName(relatedObjective.getTeam().getLocalName());
            model.setImage(relatedObjective.getTeam().getImage());
        } else if (relatedObjective.getMember() != null) {
            model.setObjectSeq(relatedObjective.getMember().getMemberSeq());
            model.setObjectName(relatedObjective.getMember().getLocalName());
            model.setImage(relatedObjective.getMember().getImage());
        }
    }

    @Override
    public void mapKeyResultCollaborators(List<ObjectiveViewOkrModel> objectives, List<KeyResultCollaboratorDto> keyResultCollaborators) {
        Map<Integer, List<KeyResultCollaboratorDto>> map = keyResultCollaborators.stream()
                .collect(Collectors.groupingBy(e -> e.getKeyResult().getKeyResultSeq(), Collectors.toList()));

        objectives.forEach(o -> {
            o.getKeyResults().forEach(k -> {
                k.setKeyResultCollaborators(
                        map.getOrDefault(k.getKeyResultSeq(), new ArrayList<>()).stream()
                                .filter(e -> e.getCollaborator().getUseFlag().equals(FlagOption.Y))
                                .map(e ->
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

    @Override
    public List<ObjectiveDto> findMemberObjective(Integer memberSeq) {
        try {
            List<Objective> objectives = objectiveRepository.findAll(
                    (objectiveQuery.isNotNull(Objective_.MEMBER))
                            .and(objectiveQuery.findByMemberSeq(memberSeq))
                    , Sort.by(Objective_.PRIORITY)
            );
            return mapper.mapAsList(objectives, ObjectiveDto.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void updateObjective(ObjectiveDto objectiveUpdateDto) {
        Objective originalObjective = objectiveRepository.findById(objectiveUpdateDto.getObjectiveSeq()).orElseThrow(() -> new UserException("No objective found with id " + objectiveUpdateDto.getObjectiveSeq()));
        originalObjective.setObjective(objectiveUpdateDto.getObjective());
        originalObjective.setProportion(objectiveUpdateDto.getProportion());
        // currently I have no idea if system allow user to edit closed objective
        if (objectiveUpdateDto.isClosed() && !originalObjective.isClosed()) {
            originalObjective.setCloseDate(Instant.now());
            originalObjective.setCloseJustification(objectiveUpdateDto.getCloseJustification());
            originalObjective.getKeyResults().stream().filter(keyResult -> !keyResult.isClosed()).forEach(activeKeyResult -> {
                activeKeyResult.setCloseFlag(FlagOption.Y);
                activeKeyResult.setCloseDate(DateTimeUtils.todayDBString());
                activeKeyResult.setCloseJustification(objectiveUpdateDto.getCloseJustification());
                KeyResultHistory keyResultHistory = mapper.map(activeKeyResult, KeyResultHistory.class);
                keyResultHistory.setKeyResultObject(activeKeyResult);
                keyResultRepository.save(activeKeyResult);
                keyResultHistoryRepository.save(keyResultHistory);
            });
        }
        originalObjective.setCloseFlag(objectiveUpdateDto.getCloseFlag());
        ObjectiveHistory objectiveHistory = mapper.map(originalObjective, ObjectiveHistory.class);
        objectiveHistory.setJustification(objectiveUpdateDto.getJustification());
        objectiveHistory.setObjectiveObject(originalObjective);
        objectiveRepository.save(originalObjective);
        objectiveHistoryRepository.save(objectiveHistory);
    }

    @Override
    public void mapEditableForObjectiveModel(List<ObjectiveViewOkrModel> objectives,
                                             List<EvaluationObjectiveDto> evaluationObjectiveDtos,
                                             boolean isCurrentMemberCanEditOkr) {
        Map<Integer, String> map = evaluationObjectiveDtos.stream()
                .collect(Collectors.toMap(e -> e.getEvaluationObjectiveId().getObjective().getObjectiveSeq(),
                        e -> e.getEvaluationObjectiveId().getEvaluationOkr().getQuarterEndDate()));

        objectives.forEach(o -> {
            o.setEditable(isCurrentMemberCanEditOkr
                    && Optional.ofNullable(map.getOrDefault(o.getObjectiveSeq(), null))
                    .map(e -> e.compareToIgnoreCase(DateTimeUtils.getCurrentDateInString()) >= 0).orElse(true));
        });
    }

}