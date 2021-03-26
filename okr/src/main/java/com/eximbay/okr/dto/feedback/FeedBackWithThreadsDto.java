package com.eximbay.okr.dto.feedback;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.feedbackThread.FeedbackThreadDto;
import com.eximbay.okr.entity.FeedbackThread;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Data
@ToString(exclude = { "feedbackThreads"})
@EqualsAndHashCode(exclude = { "feedbackThreads"})
public class FeedBackWithThreadsDto {

    private Integer feedbackSeq;
    private String sourceTable;
    private Integer sourceSeq;
    private String feedback;
    private MemberDto member;
    private Integer likes = 0;
    private List<FeedbackThreadDto> feedbackThreads;
    protected Instant createdDate;
    protected Instant updatedDate;
}
