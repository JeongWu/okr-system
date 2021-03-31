package com.eximbay.okr.dto.division;

import com.eximbay.okr.entity.Division;
import com.eximbay.okr.entity.Member;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddDivisionMemberFormDto {
    @NotNull
    private Division division;
    @NotNull
    private Member member;
    @NotBlank
    private String applyBeginDate;
    private String applyEndDate;
    private String justification;
}
