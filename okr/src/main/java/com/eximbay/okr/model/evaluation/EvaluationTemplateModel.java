package com.eximbay.okr.model.evaluation;

import com.eximbay.okr.dto.codelist.CodeListDto;
import com.eximbay.okr.dto.evaluation.EvaluationTemplateDto;
import com.eximbay.okr.dto.evaluationcriteria.EvaluationCriteriaDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EvaluationTemplateModel {
    private String subheader;
    private String mutedHeader;
    private List<CodeListDto> evaluationTypes;
    private List<EvaluationCriteriaDto> answerGroupCode;

    private EvaluationTemplateDto evaluationTemplate;
}
