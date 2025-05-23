package com.lgvt.audit_log_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lgvt.audit_log_service.dao.AuditDAOImpl;
import com.lgvt.audit_log_service.entity.Audit;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditDAOImpl auditDAO;

    public AuditServiceImpl(AuditDAOImpl auditDAO) {
        this.auditDAO = auditDAO;
    }

    @Override
    public List<Audit> getAllAuditLogs() {
        return auditDAO.findAll();
    }

    @Override
    public Audit createAuditLog(Audit audit) {
        audit.setTimestamp(LocalDateTime.now());
        return auditDAO.save(audit);
    }
}
