package com.lgvt.audit_log_service.entity;

public enum AuditAction {
    USER_CREATE,
    USER_UPDATE,
    AUTH_FAILURE,
    VOTE_CAST,
    REPORT_EXPORT,
    SETTINGS_UPDATE,
    EVM_DATA_IMPORT,
    BACKUP_CREATED,
    PASSWORD_RESET,
    ACCOUNT_LOCKED,
    USER_APPROVE
}