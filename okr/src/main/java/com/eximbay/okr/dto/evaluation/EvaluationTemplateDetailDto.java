package com.eximbay.okr.dto.evaluation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationTemplateDetailDto {

    private Integer detailSeq;
    private Integer templateSeq;
    private String question;
    private String answerGroupCode;
    private String useFlag;
}
