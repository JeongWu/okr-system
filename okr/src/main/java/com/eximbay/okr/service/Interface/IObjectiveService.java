package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.evaluationobjective.EvaluationObjectiveDto;
import com.eximbay.okr.dto.keyresultcollaborator.KeyResultCollaboratorDto;
import com.eximbay.okr.dto.like.LikeDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.objective.UpdateObjectivePriorityRequest;
import com.eximbay.okr.dto.objectiverelation.ObjectiveRelationDto;
import com.eximbay.okr.model.feedback.FeedbackForViewOkrModel;
import com.eximbay.okr.model.objective.ObjectiveViewOkrModel;

import java.util.List;

public interface IObjectiveService extends IService<ObjectiveDto, Integer> {
    List<ObjectiveDto> findAllInUse();

    List<String> findAllQuarters();

    List<ObjectiveDto> findAllInQuarter(String quarter);

    List<ObjectiveViewOkrModel> findAllCompanyObjectivesOkrInQuarter(String quarterString);

    void updateObjectivePriority(UpdateObjectivePriorityRequest request);

    List<ObjectiveViewOkrModel> findTeamObjectivesOkrInQuarterByTeamSeq(Integer teamSeq, String quarterString);

    List<ObjectiveViewOkrModel> findMemberObjectivesOkrInQuarterByMemberSeq(Integer memberSeq, String quarterString);

    void mapFeedbackIntoObjectiveModel(List<ObjectiveViewOkrModel> objectives, List<FeedbackForViewOkrModel> feedbacks);

    void mapLikesIntoObjectiveModel(List<ObjectiveViewOkrModel> objectives, List<LikeDto> likes);

    void mapObjectiveRelationsIntoObjectiveModel(List<ObjectiveViewOkrModel> objectives, List<ObjectiveRelationDto> objectiveRelations);

    void mapKeyResultCollaborators(List<ObjectiveViewOkrModel> objectives, List<KeyResultCollaboratorDto> keyResultCollaborators);

    void mapEditableForObjectiveModel(List<ObjectiveViewOkrModel> objectives, List<EvaluationObjectiveDto> evaluationObjectiveDtos, boolean isCurrentMemberCanEditOkr);

    List<ObjectiveDto> findMemberObjective(Integer memberSeq);

    void updateObjective(ObjectiveDto objectiveUpdateDto);
}
