package com.eximbay.okr.service.specification;

import com.eximbay.okr.entity.KeyResult;
import com.eximbay.okr.entity.KeyResult_;
import com.eximbay.okr.entity.Objective_;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class KeyResultQuery {

    public Specification<KeyResult> findByObjectiveSeqIn(List<Integer> in){
        return (root, query, cb) -> root.get(KeyResult_.OBJECTIVE).get(Objective_.OBJECTIVE_SEQ).in(in);
    }
}
