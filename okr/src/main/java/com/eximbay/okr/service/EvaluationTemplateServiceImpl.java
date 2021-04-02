package com.eximbay.okr.service;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.GroupCode;
import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.evaluation.EvaluationTemplateDetailDto;
import com.eximbay.okr.dto.evaluation.EvaluationTemplateDto;
import com.eximbay.okr.entity.evaluation.EvaluationTemplate;
import com.eximbay.okr.entity.evaluation.EvaluationTemplateDetail;
import com.eximbay.okr.exception.UserException;
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

    @Override
    public EvaluationTemplateModel buildEvaluationTemplateModel(int templateId) {
        EvaluationTemplateDto evaluationTemplateDto = this.findById(templateId).orElseThrow(() -> new UserException("Cannot find template ID: " + templateId));
        EvaluationTemplateModel editTemplateModel = this.buildEvaluationTemplateModel();
        editTemplateModel.setMutedHeader(Subheader.EDIT_TEMPLATE);
        editTemplateModel.setEvaluationTemplate(evaluationTemplateDto);
        return editTemplateModel;
    }

    @Override
    public void updateEvaluationTemplate(EvaluationTemplateDto userTemplateDto) {
        EvaluationTemplate evalTemplate = evalTemplateRepository.findById(userTemplateDto.getTemplateSeq()).orElseThrow(() -> new UserException("Cannot find template ID: " + userTemplateDto.getTemplateSeq()));
        evalTemplate.setTemplateName(userTemplateDto.getTemplateName());
        evalTemplate.setEvaluationType(userTemplateDto.getEvaluationType());
        evalTemplate.setUseFlag(userTemplateDto.getUseFlag());
        evalTemplateRepository.save(evalTemplate);
    }

    @Override
    public void updateEvaluationTemplateDetails(EvaluationTemplateDto userTemplateDto) {
        EvaluationTemplate evalTemplate = evalTemplateRepository.findById(userTemplateDto.getTemplateSeq()).orElseThrow(() -> new UserException("Cannot find template ID: " + userTemplateDto.getTemplateSeq()));
        for (EvaluationTemplateDetailDto evalDetailDto : userTemplateDto.getEvaluationTemplateDetails()) {
            if (evalDetailDto.getDetailSeq() != null) {
                this.updateEvaluationTemplateDetail(evalDetailDto);
            } else {
                this.addEvaluationTemplateDetail(evalTemplate, evalDetailDto);
            }
        }
    }

    private void updateEvaluationTemplateDetail(EvaluationTemplateDetailDto userEvalDetailDto) {
        EvaluationTemplateDetail existingDetail = evalTemplateDetailRepository.findById(userEvalDetailDto.getDetailSeq()).orElseThrow(() -> new UserException("Cannot find template detail seq: " + userEvalDetailDto.getDetailSeq()));
        existingDetail.setQuestion(userEvalDetailDto.getQuestion());
        existingDetail.setAnswerGroupCode(userEvalDetailDto.getAnswerGroupCode());
        existingDetail.setUseFlag(userEvalDetailDto.getUseFlag());
        evalTemplateDetailRepository.save(existingDetail);
    }

    private void addEvaluationTemplateDetail(EvaluationTemplate evalTemplate, EvaluationTemplateDetailDto userEvalDetailDto) {
        EvaluationTemplateDetail newEvalDetail = mapper.map(userEvalDetailDto, EvaluationTemplateDetail.class);
        newEvalDetail.setEvaluationTemplate(evalTemplate);
        evalTemplateDetailRepository.save(newEvalDetail);
    }

}
