package com.eximbay.okr.dto.feedback;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.model.common.LikeModel;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = { "parentFeedback"})
public class FeedbackForOkrViewDetailsDto {

    private Integer feedbackSeq;
    private String sourceTable;
    private Integer sourceSeq;
    private String feedback;
    private MemberDto member;
    private FeedbackForOkrViewDetailsDto parentFeedback;
    private Integer depth;
    private String useFlag;
    protected Instant createdDate;
    protected Instant updatedDate;
    private LikeModel likeModel;
    private boolean deletable;

    private List<FeedbackForOkrViewDetailsDto> subFeedbacks = new ArrayList<>();
}
