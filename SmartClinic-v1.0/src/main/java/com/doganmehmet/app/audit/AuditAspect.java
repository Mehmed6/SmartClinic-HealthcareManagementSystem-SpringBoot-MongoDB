package com.doganmehmet.app.audit;

import com.doganmehmet.app.entity.AuditLog;
import com.doganmehmet.app.kafka.AuditEvent;
import com.doganmehmet.app.kafka.AuditKafkaProducer;
import com.doganmehmet.app.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditKafkaProducer m_auditKafkaProducer;

    @AfterReturning("@annotation(com.doganmehmet.app.audit.Auditable)")
    public void audit(JoinPoint joinPoint)
    {
        var signature = (MethodSignature)joinPoint.getSignature();
        var method = signature.getMethod();
        var auditable = method.getAnnotation(Auditable.class);

        var performedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        var action = auditable.action();
        var entity = auditable.entity();
        var description = auditable.description();
        var entityId = joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0].toString() : null;

        var auditEvent = new AuditEvent(performedBy, action, entity, entityId, description, java.time.LocalDateTime.now());

        m_auditKafkaProducer.send(auditEvent);
    }
}
