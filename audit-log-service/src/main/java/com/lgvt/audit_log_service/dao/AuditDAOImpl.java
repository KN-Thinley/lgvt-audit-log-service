package com.lgvt.audit_log_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lgvt.audit_log_service.entity.Audit;

@Repository
public interface AuditDAOImpl extends JpaRepository<Audit, Long> {
}
