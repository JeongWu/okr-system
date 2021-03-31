package com.eximbay.okr.service.specification;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.KeyResultCollaborator;
import com.eximbay.okr.entity.KeyResultCollaborator_;
import com.eximbay.okr.entity.KeyResult_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyResultCollaboratorQuery {

    public Specification<KeyResultCollaborator> findByKeyResultSeqIn(List<Integer> in) {
        return (root, query, cb) -> root.get(KeyResultCollaborator_.KEY_RESULT).get(KeyResult_.KEY_RESULT_SEQ).in(in);
    }

    public Specification<KeyResultCollaborator> findActiveKeyResultCollaborator() {
        return (root, query, cb) -> cb.equal(root.get(KeyResultCollaborator_.USE_FLAG), FlagOption.Y);
    }

    public Specification<KeyResultCollaborator> findByCollaboratorNotNull() {
        return (root, query, cb) -> root.get(KeyResultCollaborator_.COLLABORATOR).isNotNull();
    }
}
