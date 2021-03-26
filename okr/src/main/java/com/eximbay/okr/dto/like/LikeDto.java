package com.eximbay.okr.dto.like;

import com.eximbay.okr.dto.MemberDto;
import lombok.Data;

import java.time.Instant;

@Data
public class LikeDto {

    private Integer likeSeq;
    private String sourceTable;
    private Integer sourceSeq;
    private MemberDto member;
    protected Instant createdDate;
    protected Instant updatedDate;
}
