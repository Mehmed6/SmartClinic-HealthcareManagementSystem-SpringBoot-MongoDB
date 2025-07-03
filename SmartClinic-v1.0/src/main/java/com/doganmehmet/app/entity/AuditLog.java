package com.doganmehmet.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audit_logs")
public class AuditLog extends BaseEntity{

    private String performedBy;
    private String action;
    private String entityName;
    private String entityId;
    private String description;
    @CreationTimestamp
    private LocalDateTime timestamp;
}
