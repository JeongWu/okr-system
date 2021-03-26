package com.eximbay.okr.model.auditLog;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.model.ComboBoxModel;
import lombok.Data;

import java.util.List;

@Data
public class AuditLogsModel {
    private String subheader = Subheader.AUDIT_LOGS;
    private String mutedHeader;
    private List<ComboBoxModel> logTypes;
}
