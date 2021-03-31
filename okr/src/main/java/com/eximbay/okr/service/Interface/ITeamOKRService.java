package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.model.team.AddTeamOkrModel;
import com.eximbay.okr.model.team.EditTeamOkrModel;

public interface ITeamOKRService {
    AddTeamOkrModel buildAddTeamOkrModel(int teamId);

    void addObjectiveAndKeyResult(int teamId, ObjectiveDto objectiveDto);

    EditTeamOkrModel buildEditTeamOkrDataModel(int teamId, int objectiveId);
}
