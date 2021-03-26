package com.eximbay.okr.model.objective;

import com.eximbay.okr.model.keyResult.KeyResultCompanyOkrModel;
import com.eximbay.okr.model.objectiveRelation.ObjectiveRelationForCompanyOkrModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyObjectiveOkrModel {

    private Integer objectiveSeq;
    private String objective;
    private int priority;
    private int progress;
    private String closeFlag;
    private Integer likes;
    private Long feedbackCount;
    private List<KeyResultCompanyOkrModel> keyResults = new ArrayList<>();
    private List<ObjectiveRelationForCompanyOkrModel> relatedObjectives = new ArrayList<>();
}
