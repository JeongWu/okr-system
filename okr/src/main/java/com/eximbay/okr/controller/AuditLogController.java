package com.eximbay.okr.controller;

import com.eximbay.okr.model.auditLog.AuditLogsModel;
import com.eximbay.okr.service.Interface.IAuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/audit-logs")
public class AuditLogController {

    private final IAuditLogService auditLogService;

    @GetMapping
    public String getModel(Model model) {
        AuditLogsModel auditLogsModel = auditLogService.buildAuditLogsModel();
        model.addAttribute("model", auditLogsModel);
        return "pages/auditlogs/audit-logs";
    }
}
