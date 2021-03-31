package com.eximbay.okr.service;

import com.eximbay.okr.dto.evaluationokr.EvaluationOkrDto;
import com.eximbay.okr.entity.EvaluationOkr;
import com.eximbay.okr.entity.EvaluationOkr_;
import com.eximbay.okr.repository.EvaluationOkrRepository;
import com.eximbay.okr.service.Interface.IEvaluationOkrService;
import com.eximbay.okr.service.specification.EvaluationOkrQuery;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EvaluationOkrServiceImpl implements IEvaluationOkrService {

    private final MapperFacade mapper;
    private final EvaluationOkrRepository evaluationOkrRepository;
    private final EvaluationOkrQuery evaluationOkrQuery;

    @Override
    public List<EvaluationOkrDto> findAll() {
        List<EvaluationOkr> evaluationOkrs = evaluationOkrRepository.findAll();
        return mapper.mapAsList(evaluationOkrs, EvaluationOkrDto.class);
    }

    @Override
    public Optional<EvaluationOkrDto> findById(Integer id) {
        Optional<EvaluationOkr> evaluationOkr = evaluationOkrRepository.findById(id);
        return evaluationOkr.map(m -> mapper.map(m, EvaluationOkrDto.class));
    }

    @Override
    public void remove(EvaluationOkrDto evaluationOkrDto) {
        EvaluationOkr evaluationOkr = mapper.map(evaluationOkrDto, EvaluationOkr.class);
        evaluationOkrRepository.delete(evaluationOkr);
    }

    @Override
    public EvaluationOkrDto save(EvaluationOkrDto evaluationOkrDto) {
        EvaluationOkr evaluationOkr = mapper.map(evaluationOkrDto, EvaluationOkr.class);
        evaluationOkr = evaluationOkrRepository.save(evaluationOkr);
        return mapper.map(evaluationOkr, EvaluationOkrDto.class);
    }

    @Override
    public Optional<EvaluationOkr> findByQuarterStringAndObjectiveType(String quarterString, String objectiveType) {
        try {
            Integer year = Integer.valueOf(quarterString.substring(0, 4));
            Integer quarter = Integer.valueOf(quarterString.substring(5, 6));
            Optional<EvaluationOkr> evaluationOkr = evaluationOkrRepository.findAll(
                    evaluationOkrQuery.isEqual(EvaluationOkr_.OBJECTIVE_TYPE, objectiveType)
                            .and(evaluationOkrQuery.isEqual(EvaluationOkr_.YEAR, year))
                            .and(evaluationOkrQuery.isEqual(EvaluationOkr_.QUARTER, quarter))
            ).stream().findAny();
            return evaluationOkr;
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
