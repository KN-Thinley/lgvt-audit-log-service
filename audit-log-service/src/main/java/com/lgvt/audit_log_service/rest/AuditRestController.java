package com.lgvt.audit_log_service.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgvt.audit_log_service.entity.Audit;
import com.lgvt.audit_log_service.service.AuditService;

@RestController
@RequestMapping("/api/audits")
public class AuditRestController {

    private final AuditService auditService;

    public AuditRestController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public ResponseEntity<List<Audit>> getAllAudits() {
        return ResponseEntity.ok(auditService.getAllAuditLogs());
    }

    @PostMapping
    public ResponseEntity<Audit> createAudit(@RequestBody Audit audit) {
        return ResponseEntity.ok(auditService.createAuditLog(audit));
    }
}
