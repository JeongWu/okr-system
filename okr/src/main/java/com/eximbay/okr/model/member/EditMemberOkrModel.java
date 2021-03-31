package com.eximbay.okr.model.member;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import lombok.Data;

@Data
public class EditMemberOkrModel extends MemberOkrCommonModel {
    private ObjectiveDto objectiveDto;
}
