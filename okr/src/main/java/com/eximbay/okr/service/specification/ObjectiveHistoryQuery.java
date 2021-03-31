package com.eximbay.okr.service.specification;

import com.eximbay.okr.entity.ObjectiveHistory;
import com.eximbay.okr.entity.ObjectiveHistory_;
import com.eximbay.okr.entity.Objective_;
import com.eximbay.okr.service.Interface.IGenericQuery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ObjectiveHistoryQuery implements IGenericQuery<ObjectiveHistory> {

    public Specification<ObjectiveHistory> findByObjectiveSeq(Integer objectiveSeq) {
        return (root, query, cb) -> cb.equal(root.get(ObjectiveHistory_.OBJECTIVE_OBJECT).get(Objective_.OBJECTIVE_SEQ), objectiveSeq);
    }
}
