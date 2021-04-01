package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.evaluation.EvaluationTemplateDto;
import com.eximbay.okr.model.evaluation.EvaluationTemplateModel;

public interface IEvaluationTemplateService extends IService<EvaluationTemplateDto, Integer> {

    EvaluationTemplateModel buildEvaluationTemplateModel();

    void addEvaluationTemplate(EvaluationTemplateDto evaluationTemplateDto);
}
