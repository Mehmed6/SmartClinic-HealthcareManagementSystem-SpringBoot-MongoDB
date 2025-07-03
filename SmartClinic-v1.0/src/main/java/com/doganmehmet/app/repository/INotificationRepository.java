package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.Notification;
import com.doganmehmet.app.enums.NotificationStatus;
import com.doganmehmet.app.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface INotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByStatusAndSentAtBefore(NotificationStatus status, LocalDateTime sentAtBefore);

    List<Notification> findAllByStatus(NotificationStatus status);

    List<Notification> findAllByType(NotificationType type);

    List<Notification> findAllBySentAtBefore(LocalDateTime sentAtBefore);

    List<Notification> findAllBySentAtAfter(LocalDateTime sentAtAfter);
}
