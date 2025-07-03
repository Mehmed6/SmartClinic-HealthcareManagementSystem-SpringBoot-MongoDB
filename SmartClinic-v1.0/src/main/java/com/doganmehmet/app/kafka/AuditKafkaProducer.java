package com.doganmehmet.app.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditKafkaProducer {
    private final KafkaTemplate<String, AuditEvent> m_kafkaTemplate;

    public void send(AuditEvent auditEvent)
    {
        m_kafkaTemplate.send("audit-log", auditEvent);
    }
}
