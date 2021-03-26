package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.dto.keyResultCollaborator.KeyResultCollaboratorDto;
import com.eximbay.okr.dto.like.LikeDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.objective.UpdateObjectivePriorityRequest;
import com.eximbay.okr.dto.objectiveRelation.ObjectiveRelationDto;
import com.eximbay.okr.model.feedback.FeedbackForViewOkrModel;
import com.eximbay.okr.model.objective.ObjectiveViewOkrModel;

import java.util.*;

public interface IObjectiveService extends ISerivce<ObjectiveDto, Integer>{
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
    List<ObjectiveDto> findMemberObjective(Integer memberSeq);
}
