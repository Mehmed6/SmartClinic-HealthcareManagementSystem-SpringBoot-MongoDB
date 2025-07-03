package com.doganmehmet.app.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditEvent {
    private String performedBy;
    private String action;
    private String entityName;
    private String entityId;
    private String description;
    private LocalDateTime timestamp;
}
