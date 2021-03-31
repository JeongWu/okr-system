package com.eximbay.okr.service.specification;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.ObjectiveRelation;
import com.eximbay.okr.entity.ObjectiveRelation_;
import com.eximbay.okr.entity.Objective_;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class ObjectiveRelationQuery {

    public Specification<ObjectiveRelation> findActiveObjectiveRelation() {
        return (root, query, cb) -> cb.equal(root.get(ObjectiveRelation_.USE_FLAG), FlagOption.Y);
    }

    public Specification<ObjectiveRelation> findByObjectiveSeqIn(List<Integer> in) {
        return (root, query, cb) -> root.get(ObjectiveRelation_.OBJECTIVE).get(Objective_.OBJECTIVE_SEQ).in(in);
    }

    public Specification<ObjectiveRelation> findByRelatedObjectiveNotNull() {
        return (root, query, cb) -> root.get(ObjectiveRelation_.RELATED_OBJECTIVE).isNotNull();
    }

}
