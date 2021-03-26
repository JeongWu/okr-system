package com.eximbay.okr.dto.auditLog;

import com.eximbay.okr.enumeration.LogType;
import lombok.Data;

import java.time.Instant;

@Data
public class AuditLogDto {
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
