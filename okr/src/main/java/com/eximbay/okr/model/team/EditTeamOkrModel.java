package com.eximbay.okr.model.team;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import lombok.Data;

@Data
public class EditTeamOkrModel extends TeamOkrCommonModel {
    private ObjectiveDto objectiveDto;
}
