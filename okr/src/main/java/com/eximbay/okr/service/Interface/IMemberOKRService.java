package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.model.member.AddMemberOkrModel;
import com.eximbay.okr.model.member.EditMemberOkrModel;

public interface IMemberOKRService {
    AddMemberOkrModel buildAddMemberOkrModel(int memberId);

    void addObjectiveAndKeyResult(int memberId, ObjectiveDto objectiveKeyResultsDto);

    EditMemberOkrModel buildEditMemberOkrDataModel(int memberId, int objectiveId);
}
