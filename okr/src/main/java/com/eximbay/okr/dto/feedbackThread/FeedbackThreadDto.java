package com.eximbay.okr.dto.feedbackThread;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.feedback.FeedbackDto;
import lombok.Data;

import java.time.Instant;

@Data
public class FeedbackThreadDto {

    private Integer threadSeq;
    private FeedbackDto feedback;
    private Integer likes;
    private String thread;
    private MemberDto member;
    protected Instant createdDate;
    protected Instant updatedDate;
}
