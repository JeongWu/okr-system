package com.eximbay.okr.dto;

import com.eximbay.okr.entity.*;
import lombok.*;

import javax.validation.constraints.*;

@Data
public class RemoveDivisionMemberFormDto {
    @NotNull
    private Division division;
    @NotNull
    private Member member;
    @NotBlank
    private String applyBeginDate;
    @NotBlank
    private String applyEndDate;
    private String justification;
}
