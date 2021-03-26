package com.eximbay.okr.dto.feedback;

import com.eximbay.okr.dto.MemberDto;
import lombok.Data;

import java.time.Instant;

@Data
public class FeedBackForCompanyDashboardDto {
    private Integer feedbackSeq;
    private String sourceTable;
    private Integer sourceSeq;
    private String feedback;
    private MemberDto member;
    protected Instant createdDate;
    protected Instant updatedDate;
    private Long likes;
}
