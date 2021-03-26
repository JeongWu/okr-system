package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.like.LikeDto;

import java.util.List;

public interface ILikeService extends ISerivce<LikeDto, Integer> {
    List<LikeDto> getLikeForViewOkr(List<Integer> objectivesSeq, List<Integer> keyResultsSeq);
    List<LikeDto> getLikeForCompanyDashboard(List<Integer> feedbacksSeq);
}
