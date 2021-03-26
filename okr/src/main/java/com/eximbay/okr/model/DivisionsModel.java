package com.eximbay.okr.model;

import com.eximbay.okr.constant.Subheader;
import lombok.Data;

import java.util.List;

@Data
public class DivisionsModel {
    private String subheader = Subheader.DIVISION;
    private String mutedHeader;
    private List<DivisionForDivisionsModel> divisions;
}
