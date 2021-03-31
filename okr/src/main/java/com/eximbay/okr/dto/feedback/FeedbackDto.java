package com.eximbay.okr.dto.feedback;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class FeedbackDto extends AbstractAuditableDto {

    private Integer feedbackSeq;
    private String sourceTable;
    private Integer sourceSeq;
    private String feedback;
    private MemberDto member;
    private FeedbackDto parentFeedback;
    private Integer depth;
    private String useFlag;
    protected Instant createdDate;
    protected Instant updatedDate;
    protected Instant deletedDate;
}
