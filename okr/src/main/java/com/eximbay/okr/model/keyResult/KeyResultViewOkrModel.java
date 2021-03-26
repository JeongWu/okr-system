package com.eximbay.okr.model.keyResult;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.model.keyResultCollaborator.KeyResultCollaboratorForCompanyOkrModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KeyResultViewOkrModel {

    private Integer keyResultSeq;
    private ObjectiveDto objective;
    private String keyResult;
    private String shortenKeyResult;
    private Integer proportion;
    private Integer progress;
    private String closeFlag;
    private Long likes;
    private Long feedbackCount;
    private List<KeyResultCollaboratorForCompanyOkrModel> keyResultCollaborators = new ArrayList<>();
}
