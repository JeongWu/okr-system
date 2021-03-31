package com.eximbay.okr.dto.feedback;

import lombok.Data;

@Data
public class AddFeedbackRequestDto {

    private String sourceTable;
    private String sourceSeq;
    private String feedback;
    private Integer parentFeedbackSeq;
}
