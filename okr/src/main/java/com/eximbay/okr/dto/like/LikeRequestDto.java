package com.eximbay.okr.dto.like;

import lombok.Data;

@Data
public class LikeRequestDto {

    private String sourceTable;
    private String sourceSeq;
    private String action;
}
