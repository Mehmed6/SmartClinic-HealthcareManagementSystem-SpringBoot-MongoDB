package com.doganmehmet.app.kafka;

import com.doganmehmet.app.entity.AuditLog;
import com.doganmehmet.app.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditKafkaConsumer {

    private final AuditLogRepository m_auditLogRepository;

    @KafkaListener(topics = "audit-log", groupId = "audit-log-group")
    public void listen(AuditEvent auditEvent)
    {
        var log = new AuditLog();
        log.setPerformedBy(auditEvent.getPerformedBy());
        log.setAction(auditEvent.getAction());
        log.setEntityName(auditEvent.getEntityName());
        log.setEntityId(auditEvent.getEntityId());
        log.setDescription(auditEvent.getDescription());
        log.setTimestamp(auditEvent.getTimestamp());

        m_auditLogRepository.save(log);
    }
}
