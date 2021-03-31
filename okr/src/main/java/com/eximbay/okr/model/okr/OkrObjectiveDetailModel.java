package com.eximbay.okr.model.okr;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.feedback.FeedbackForOkrViewDetailsDto;
import com.eximbay.okr.model.common.LikeModel;
import lombok.Data;

import java.util.List;

@Data
public class OkrObjectiveDetailModel {

    private String subheader = Subheader.VIEW_OKR_DETAIL;
    private String mutedHeader;
    private Integer objectiveSeq;
    private String objective;
    private LikeModel likeModel;
    private List<FeedbackForOkrViewDetailsDto> feedbacks;
    private MemberDto currentMember;
}
