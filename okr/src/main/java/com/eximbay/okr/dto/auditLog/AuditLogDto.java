package com.eximbay.okr.dto.auditlog;

import com.eximbay.okr.enumeration.LogType;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuditLogDto extends AbstractAuditableDto {

    private Integer logSeq;
    private LogType logType;
    private String email;
    private String name;
    private String target;
    private String parameter;
    private String comment;
    private String accessIp;
    protected Instant createdDate;
}
