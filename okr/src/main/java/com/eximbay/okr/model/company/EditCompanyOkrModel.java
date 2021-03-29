package com.eximbay.okr.model.company;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import lombok.Data;

@Data
public class EditCompanyOkrModel extends OkrCommonModel {
    private ObjectiveDto objectiveDto;
}
