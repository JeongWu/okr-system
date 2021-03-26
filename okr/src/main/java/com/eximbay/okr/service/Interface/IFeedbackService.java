package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.feedback.FeedBackWithThreadsDto;
import com.eximbay.okr.dto.feedback.FeedbackDto;
import com.eximbay.okr.model.feedback.FeedbackForCompanyViewOkrModel;

import java.util.List;

public interface IFeedbackService extends ISerivce<FeedbackDto, Integer> {
    List<FeedbackForCompanyViewOkrModel> getFeedbackForCompanyViewOkr(List<Integer> objectiveSeqList, List<Integer> keyResultSeqList);
    List<FeedBackWithThreadsDto> findTop10ByOrderByCreatedDateDesc();
}
