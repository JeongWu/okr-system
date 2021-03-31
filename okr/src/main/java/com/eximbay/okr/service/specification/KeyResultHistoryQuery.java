package com.eximbay.okr.service.specification;

import com.eximbay.okr.entity.KeyResultHistory;
import com.eximbay.okr.entity.KeyResultHistory_;
import com.eximbay.okr.entity.KeyResult_;
import com.eximbay.okr.service.Interface.IGenericQuery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class KeyResultHistoryQuery implements IGenericQuery<KeyResultHistory> {

    public Specification<KeyResultHistory> findByKeyResultSeq(Integer keyResultSeq) {
        return (root, query, cb) -> cb.equal(root.get(KeyResultHistory_.KEY_RESULT_OBJECT).get(KeyResult_.KEY_RESULT_SEQ), keyResultSeq);
    }
}
