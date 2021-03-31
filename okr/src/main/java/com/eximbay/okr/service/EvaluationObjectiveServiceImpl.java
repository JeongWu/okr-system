package com.eximbay.okr.service;

import com.eximbay.okr.dto.evaluationobjective.EvaluationObjectiveDto;
import com.eximbay.okr.entity.EvaluationObjective;
import com.eximbay.okr.repository.EvaluationObjectiveRepository;
import com.eximbay.okr.service.Interface.IEvaluationObjectiveService;
import com.eximbay.okr.service.specification.EvaluationObjectiveQuery;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EvaluationObjectiveServiceImpl implements IEvaluationObjectiveService {

    private final MapperFacade mapper;
    private final EvaluationObjectiveRepository evaluationObjectiveRepository;
    private final EvaluationObjectiveQuery evaluationObjectiveQuery;

    @Override
    public List<EvaluationObjectiveDto> findAll() {
        List<EvaluationObjective> evaluationObjectives = evaluationObjectiveRepository.findAll();
        return mapper.mapAsList(evaluationObjectives, EvaluationObjectiveDto.class);
    }

    @Override
    public Optional<EvaluationObjectiveDto> findById(Integer id) {
        Optional<EvaluationObjective> evaluationObjective = evaluationObjectiveRepository.findById(id);
        return evaluationObjective.map(m -> mapper.map(m, EvaluationObjectiveDto.class));
    }

    @Override
    public void remove(EvaluationObjectiveDto evaluationObjectiveDto) {
        EvaluationObjective evaluationObjective = mapper.map(evaluationObjectiveDto, EvaluationObjective.class);
        evaluationObjectiveRepository.delete(evaluationObjective);
    }

    @Override
    public EvaluationObjectiveDto save(EvaluationObjectiveDto evaluationObjectiveDto) {
        EvaluationObjective evaluationObjective = mapper.map(evaluationObjectiveDto, EvaluationObjective.class);
        evaluationObjective = evaluationObjectiveRepository.save(evaluationObjective);
        return mapper.map(evaluationObjective, EvaluationObjectiveDto.class);
    }

    @Override
    public List<EvaluationObjectiveDto> findByObjectivesSeqIn(List<Integer> objectivesSeq) {
        List<EvaluationObjective> evaluationObjectives = evaluationObjectiveRepository.findAll(
                evaluationObjectiveQuery.findByObjectivesSeqIn(objectivesSeq)
        );
        return mapper.mapAsList(evaluationObjectives, EvaluationObjectiveDto.class);
    }
}
