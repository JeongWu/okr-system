package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.like.LikeDto;
import com.eximbay.okr.dto.like.LikeRequestDto;

import java.util.List;

public interface ILikeService extends IService<LikeDto, Integer> {
    List<LikeDto> getLikeForViewOkr(List<Integer> objectivesSeq, List<Integer> keyResultsSeq);

    List<LikeDto> getLikeByListFeedbacksSeq(List<Integer> feedbacksSeq);

    List<LikeDto> getLikesByObjectiveSeq(Integer objectiveSeq);

    void handleLikeRequest(LikeRequestDto likeRequest);

    List<LikeDto> getLikesByKeyResultSeq(Integer keyResultSeq);
}
