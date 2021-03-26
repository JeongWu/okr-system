package com.eximbay.okr.model;

import com.eximbay.okr.constant.Subheader;
import lombok.Data;

import java.util.List;

@Data
public class EditDivisionModel {
    private String subheader = Subheader.DIVISION_EDIT;
    private String mutedHeader;
    private DivisionUpdateFormModel model;
}
