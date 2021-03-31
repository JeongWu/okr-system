package com.eximbay.okr.service;

import com.eximbay.okr.dto.evaluationcriteria.EvaluationCriteriaDto;
import com.eximbay.okr.entity.EvaluationCriteria;
import com.eximbay.okr.repository.EvaluationCriteriaRepository;
import com.eximbay.okr.service.Interface.IEvaluationCriteriaService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationCriteriaImpl implements IEvaluationCriteriaService {

    private final MapperFacade mapper;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;

    @Override
    public List<EvaluationCriteriaDto> findAll() {
        List<EvaluationCriteria> evaluationCriterias = evaluationCriteriaRepository.findAll();
        return mapper.mapAsList(evaluationCriterias, EvaluationCriteriaDto.class);
    }

    @Override
    public Optional<EvaluationCriteriaDto> findById(String id) {
        Optional<EvaluationCriteria> evaluationCriteria = evaluationCriteriaRepository.findById(id);
        return evaluationCriteria.map(m -> mapper.map(m, EvaluationCriteriaDto.class));
    }

    @Override
    public void remove(EvaluationCriteriaDto evaluationCriteriaDto) {
        EvaluationCriteria evaluationCriteria = mapper.map(evaluationCriteriaDto, EvaluationCriteria.class);
        evaluationCriteriaRepository.delete(evaluationCriteria);

    }

    @Override
    public EvaluationCriteriaDto save(EvaluationCriteriaDto evaluationCriteriaDto) {
        EvaluationCriteria evaluationCriteria = mapper.map(evaluationCriteriaDto, EvaluationCriteria.class);
        evaluationCriteria = evaluationCriteriaRepository.save(evaluationCriteria);
        return mapper.map(evaluationCriteria, EvaluationCriteriaDto.class);
    }

    @Override
    public List<EvaluationCriteriaDto> findByGroupCode(String groupCode) {
        List<EvaluationCriteria> evaluationCriterias = evaluationCriteriaRepository.findByGroupCode(groupCode);
        return mapper.mapAsList(evaluationCriterias, EvaluationCriteriaDto.class);
    }

}
