package com.eximbay.okr.model.keyResult;

import com.eximbay.okr.dto.ObjectiveDto;
import com.eximbay.okr.model.keyResultCollaborator.KeyResultCollaboratorForCompanyOkrModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KeyResultCompanyOkrModel {

    private Integer keyResultSeq;
    private ObjectiveDto objective;
    private String keyResult;
    private int progress;
    private String closeFlag;
    private Integer likes;
    private Long feedbackCount;
    private List<KeyResultCollaboratorForCompanyOkrModel> keyResultCollaborators = new ArrayList<>();
}
