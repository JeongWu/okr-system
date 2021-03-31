package com.eximbay.okr.service.specification;

import com.eximbay.okr.entity.EvaluationObjective;
import com.eximbay.okr.entity.EvaluationObjective_;
import com.eximbay.okr.entity.Objective_;
import com.eximbay.okr.entity.id.EvaluationObjectiveId_;
import com.eximbay.okr.service.Interface.IGenericQuery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationObjectiveQuery implements IGenericQuery<EvaluationObjective> {

    public Specification<EvaluationObjective> findByObjectivesSeqIn(List<Integer> in) {
        return (root, query, cb) -> root.get(EvaluationObjective_.EVALUATION_OBJECTIVE_ID).get(EvaluationObjectiveId_.OBJECTIVE).get(Objective_.OBJECTIVE_SEQ).in(in);
    }
}
