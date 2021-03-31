package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.feedback.AddFeedbackRequestDto;
import com.eximbay.okr.dto.feedback.DeleteFeedbackRequestDto;
import com.eximbay.okr.dto.feedback.FeedbackDto;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.model.feedback.FeedbackForViewOkrModel;

import java.util.List;

public interface IFeedbackService extends IService<FeedbackDto, Integer> {
    List<FeedbackDto> findTop10ByOrderByCreatedDateDesc();

    List<FeedbackForViewOkrModel> getFeedbackForViewOkr(List<Integer> objectiveSeqList, List<Integer> keyResultSeqList);

    List<FeedbackDto> findByMember(Member member);

    List<FeedbackDto> getFeedbackByObjectiveSeq(Integer objectiveSeq);

    void delete(DeleteFeedbackRequestDto deleteFeedbackRequest);

    FeedbackDto addFeedback(AddFeedbackRequestDto request);

    List<FeedbackDto> getFeedbackByKeyResultsSeq(Integer keyResultSeq);
}
