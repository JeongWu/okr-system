package com.eximbay.okr.repository;

import com.eximbay.okr.entity.AuditLog;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface AuditLogRepository extends DataTablesRepository<AuditLog, Integer> {
}
