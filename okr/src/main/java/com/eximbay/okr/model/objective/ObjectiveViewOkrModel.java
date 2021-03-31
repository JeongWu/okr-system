package com.eximbay.okr.model.objective;

import com.eximbay.okr.model.keyResult.KeyResultViewOkrModel;
import com.eximbay.okr.model.objectiveRelation.ObjectiveRelationViewOkrModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ObjectiveViewOkrModel {

    private Integer objectiveSeq;
    private String objective;
    private String shortenObjective;
    private Integer priority;
    private Integer proportion;
    private Integer progress;
    private String closeFlag;
    private Long likes;
    private Long feedbackCount;
    private List<KeyResultViewOkrModel> keyResults = new ArrayList<>();
    private List<ObjectiveRelationViewOkrModel> relatedObjectives = new ArrayList<>();
    private boolean isEditable;
}
