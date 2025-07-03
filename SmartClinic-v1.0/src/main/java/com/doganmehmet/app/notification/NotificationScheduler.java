package com.doganmehmet.app.notification;

import com.doganmehmet.app.enums.NotificationStatus;
import com.doganmehmet.app.enums.NotificationType;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.repository.INotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationScheduler {

    private final INotificationRepository m_notificationRepository;
    private final EmailNotificationService m_emailNotificationService;
    private final SmsNotificationService m_smsNotificationService;

    @Scheduled(fixedRate = 60000)
    public void checkAndSendNotifications()
    {
        var now = LocalDateTime.now();

        var notifications = m_notificationRepository
                .findAllByStatusAndSentAtBefore(NotificationStatus.PENDING, now);

        for (var notification : notifications) {
            try {
                if (notification.getType() == NotificationType.EMAIL)
                    m_emailNotificationService.sendNotification(notification);
                else
                    m_smsNotificationService.sendNotification(notification);

                notification.setStatus(NotificationStatus.SENT);
            }
            catch (ApiException myError) {
                notification.setStatus(NotificationStatus.FAILED);
            }

            m_notificationRepository.save(notification);
        }
    }
}
