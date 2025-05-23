package com.lgvt.audit_log_service.service;

import java.util.List;

import com.lgvt.audit_log_service.entity.Audit;

public interface AuditService {
    List<Audit> getAllAuditLogs();

    Audit createAuditLog(Audit audit);
}
