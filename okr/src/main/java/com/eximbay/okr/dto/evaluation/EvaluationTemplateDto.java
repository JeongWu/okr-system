package com.eximbay.okr.dto.evaluation;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EvaluationTemplateDto {

    private Integer templateSeq;
    private String evaluationType;
    private String templateName;
    private String useFlag;
    
    private List<EvaluationTemplateDetailDto> evaluationTemplateDetails;
}
