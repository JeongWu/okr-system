package com.eximbay.okr.api;

import com.eximbay.okr.dto.auditlog.AuditLogsDatatablesInput;
import com.eximbay.okr.entity.AuditLog;
import com.eximbay.okr.service.Interface.IAuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/audit-logs")
public class AuditLogsAPI {

    private final IAuditLogService auditLogService;

    @PostMapping("/datatables")
    public DataTablesOutput<AuditLog> getAll(@Valid @RequestBody AuditLogsDatatablesInput input) {
        DataTablesOutput<AuditLog> auditLogs = auditLogService.getDataForDatatables(input);
        return auditLogs;
    }
}
