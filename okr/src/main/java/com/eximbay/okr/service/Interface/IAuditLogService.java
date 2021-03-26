package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.auditLog.AuditLogDto;
import com.eximbay.okr.dto.auditLog.AuditLogsDatatablesInput;
import com.eximbay.okr.entity.AuditLog;
import com.eximbay.okr.enumeration.LogType;
import com.eximbay.okr.model.auditLog.AuditLogsModel;
import org.aspectj.lang.JoinPoint;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface IAuditLogService extends ISerivce<AuditLogDto, Integer>{
    AuditLogsModel buildAuditLogsModel();
    DataTablesOutput<AuditLog> getDataForDatatables(AuditLogsDatatablesInput input);
    void log(LogType logType, HttpServletRequest request, Authentication authentication, JoinPoint joinPoint, boolean isChangeData);
}
