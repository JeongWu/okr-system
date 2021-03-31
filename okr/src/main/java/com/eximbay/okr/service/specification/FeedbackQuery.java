package com.eximbay.okr.service.specification;

import com.eximbay.okr.entity.Feedback;
import com.eximbay.okr.entity.Feedback_;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.enumeration.SourceTable;
import com.eximbay.okr.service.Interface.IGenericQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class FeedbackQuery implements IGenericQuery<Feedback> {

    public Specification<Feedback> findFeedbackFor(SourceTable source) {
        return (root, query, cb) -> cb.equal(root.get(Feedback_.SOURCE_TABLE), source.name());
    }

    public Specification<Feedback> findBySourceSeqIn(List<Integer> in) {
        return (root, query, cb) -> root.get(Feedback_.SOURCE_SEQ).in(in);
    }

    public Specification<Feedback> findObjectiveFeedback() {
        return findFeedbackFor(SourceTable.OBJECTIVE);
    }

    public Specification<Feedback> findKeyResultFeedback() {
        return findFeedbackFor(SourceTable.KEY_RESULT);
    }

    public Specification<Feedback> findByMember(Member member) {
        return (root, query, cb) -> root.get(Feedback_.MEMBER).in(member);
    }

    public Specification<Feedback> findObjectiveOrKeyResultFeedback() {
        return findObjectiveFeedback()
                .or(findKeyResultFeedback());
    }

    public Specification<Feedback> findObjectiveFeedbackBySourceSeqIn(List<Integer> in) {
        return findObjectiveFeedback()
                .and(findBySourceSeqIn(in));
    }

    public Specification<Feedback> findKeyResultFeedbackBySourceSeqIn(List<Integer> in) {
        return findKeyResultFeedback()
                .and(findBySourceSeqIn(in));
    }

    public Specification<Feedback> getFeedbackForCompanyViewOkr(List<Integer> objectiveSeqList, List<Integer> keyResultSeqList) {
        return findObjectiveFeedbackBySourceSeqIn(objectiveSeqList)
                .or(findKeyResultFeedbackBySourceSeqIn(keyResultSeqList));
    }
}
