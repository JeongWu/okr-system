package com.eximbay.okr.model.division;

import com.eximbay.okr.entity.Company;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DivisionAddModel {
    private Company company;
    @NotNull
    private String name;
    @NotNull
    private String localName;
    @NotNull
    private boolean useFlag;
    private String action;

}
