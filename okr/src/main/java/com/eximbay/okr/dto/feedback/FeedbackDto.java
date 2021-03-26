package com.eximbay.okr.dto.feedback;

import com.eximbay.okr.dto.MemberDto;
import lombok.Data;

import java.time.Instant;

@Data
public class FeedbackDto {

    private Integer feedbackSeq;
    private String sourceTable;
    private Integer sourceSeq;
    private String feedback;
    private MemberDto member;
    private Integer likes;
    protected Instant createdDate;
    protected Instant updatedDate;
}
