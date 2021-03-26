package com.eximbay.okr.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class DivisionUpdateFormModel {
    @NotNull
    private Integer divisionSeq;
    @NotBlank
    private String name;
    @NotBlank
    private String localName;
    @NotNull
    private boolean useFlag;
    @NotBlank
    private String justification;
}
