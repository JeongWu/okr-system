package com.eximbay.okr.model;

import java.util.List;
import com.eximbay.okr.constant.Subheader;
import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class AllDetailsMemberModel {

    private String subheader = Subheader.MEMBERS;
    private Page<MemberForAllDetailsMemberModel> membersPage;
    private List<Long> navigationPageNumbers;
    private String mutedHeader;
}
