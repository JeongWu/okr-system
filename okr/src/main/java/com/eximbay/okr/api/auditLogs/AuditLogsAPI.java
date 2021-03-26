package com.eximbay.okr.api.auditLogs;

import com.eximbay.okr.dto.auditLog.AuditLogsDatatablesInput;
import com.eximbay.okr.entity.AuditLog;
import com.eximbay.okr.service.Interface.IAuditLogService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("api/audit-logs")
public class AuditLogsAPI {
    private final IAuditLogService auditLogService;

    @PostMapping("/datatables")
    public DataTablesOutput<AuditLog> getAll(@Valid @RequestBody AuditLogsDatatablesInput input) {
        DataTablesOutput<AuditLog> auditLogs = auditLogService.getDataForDatatables(input);
        return auditLogs;
    }
}
