package com.eximbay.okr.dto.weeklyprs;

import java.util.List;

import com.eximbay.okr.dto.member.MemberDto;

public class MemberDatatableDto {
    
    private Integer memberSeq;
    private String name;
    private String localName;
    private String beginDate;
    private String endDate;

    private List<MemberDto> members;
}
