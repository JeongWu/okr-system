package com.eximbay.okr.dto.weekly;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class DateInput {
    private String year;
    private String week;
}
