package com.eximbay.okr.dto.feedback;

import com.eximbay.okr.dto.member.MemberDto;
import lombok.Data;

import java.time.Instant;

@Data
public class AddFeedbackResponseDto {

    private Integer feedbackSeq;
    private Integer depth;
    private String feedback;
    private MemberDto member;
    protected Instant createdDate;
}
