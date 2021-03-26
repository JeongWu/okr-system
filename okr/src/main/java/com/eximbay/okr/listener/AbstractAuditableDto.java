package com.eximbay.okr.listener;

import lombok.Data;

import java.time.Instant;

@Data
public class AbstractAuditableDto {
    protected String createdBy = "system";
    protected String updatedBy = "system";
    protected Instant createdDate;
    protected Instant updatedDate;
}
