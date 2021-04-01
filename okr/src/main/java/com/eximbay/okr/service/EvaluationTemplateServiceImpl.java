package com.eximbay.okr.service;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.GroupCode;
import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.evaluation.EvaluationTemplateDetailDto;
import com.eximbay.okr.dto.evaluation.EvaluationTemplateDto;
import com.eximbay.okr.entity.evaluation.EvaluationTemplate;
import com.eximbay.okr.entity.evaluation.EvaluationTemplateDetail;
import com.eximbay.okr.model.evaluation.EvaluationTemplateModel;
import com.eximbay.okr.repository.EvaluationTemplateDetailRepository;
import com.eximbay.okr.repository.EvaluationTemplateRepository;
import com.eximbay.okr.service.Interface.ICodeListService;
import com.eximbay.okr.service.Interface.IEvaluationCriteriaService;
import com.eximbay.okr.service.Interface.IEvaluationTemplateService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationTemplateServiceImpl implements IEvaluationTemplateService {

    private final MapperFacade mapper;
    private final EvaluationTemplateRepository evalTemplateRepository;
    private final EvaluationTemplateDetailRepository evalTemplateDetailRepository;
    private final ICodeListService codeListService;
    private final IEvaluationCriteriaService evaluationCriteriaService;


    @Override
    public List<EvaluationTemplateDto> findAll() {
        List<EvaluationTemplate> resultList = evalTemplateRepository.findAll();
        return mapper.mapAsList(resultList, EvaluationTemplateDto.class);
    }

    @Override
    public Optional<EvaluationTemplateDto> findById(Integer id) {
        return evalTemplateRepository.findById(id).map(t -> mapper.map(t, EvaluationTemplateDto.class));
    }

    @Override
    public void remove(EvaluationTemplateDto evaluationTemplateDto) {
        evalTemplateRepository.delete(mapper.map(evaluationTemplateDto, EvaluationTemplate.class));
    }

    @Override
    public EvaluationTemplateDto save(EvaluationTemplateDto evaluationTemplateDto) {
        EvaluationTemplate saveResult = mapper.map(evaluationTemplateDto, EvaluationTemplate.class);
        saveResult = evalTemplateRepository.save(saveResult);
        return mapper.map(saveResult, EvaluationTemplateDto.class);
    }

    @Override
    public EvaluationTemplateModel buildEvaluationTemplateModel() {
        EvaluationTemplateModel addTemplateModel = new EvaluationTemplateModel();
        addTemplateModel.setSubheader(Subheader.PERFORMANCE_EVALUATION);
        addTemplateModel.setMutedHeader(Subheader.ADD_TEMPLATE);
        addTemplateModel.setEvaluationTypes(codeListService.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.EVALUATION_TYPE, FlagOption.Y));
        addTemplateModel.setAnswerGroupCode(evaluationCriteriaService.findByGroupCodeLike(GroupCode.ANSWER_GROUP_CODE + "%"));
        return addTemplateModel;
    }

    @Override
    public void addEvaluationTemplate(EvaluationTemplateDto evaluationTemplateDto) {
        EvaluationTemplate evaluationTemplate = mapper.map(evaluationTemplateDto, EvaluationTemplate.class);
        evalTemplateRepository.save(evaluationTemplate);
        for (EvaluationTemplateDetail detailEntity : evaluationTemplate.getEvaluationTemplateDetails()) {
            detailEntity.setEvaluationTemplate(evaluationTemplate);
            evalTemplateDetailRepository.save(detailEntity);
        }
    }

}
