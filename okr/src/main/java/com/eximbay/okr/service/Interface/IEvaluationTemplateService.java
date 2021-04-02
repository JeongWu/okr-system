package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.evaluation.EvaluationTemplateDto;
import com.eximbay.okr.model.evaluation.EvaluationTemplateModel;

public interface IEvaluationTemplateService extends IService<EvaluationTemplateDto, Integer> {

    EvaluationTemplateModel buildEvaluationTemplateModel();

    EvaluationTemplateModel buildEvaluationTemplateModel(int templateId);

    void addEvaluationTemplate(EvaluationTemplateDto evaluationTemplateDto);

    void updateEvaluationTemplate(EvaluationTemplateDto evaluationTemplateDto);

    void updateEvaluationTemplateDetails(EvaluationTemplateDto evaluationTemplateDto);
}
