package com.doganmehmet.app.service;

import com.doganmehmet.app.audit.Auditable;
import com.doganmehmet.app.dto.request.NotificationRequest;
import com.doganmehmet.app.dto.response.NotificationDTO;
import com.doganmehmet.app.enums.NotificationStatus;
import com.doganmehmet.app.enums.NotificationType;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.INotificationMapper;
import com.doganmehmet.app.repository.INotificationRepository;
import com.doganmehmet.app.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final IUserRepository m_userRepository;
    private final INotificationRepository m_notificationRepository;
    private final INotificationMapper m_notificationMapper;

    @Auditable(
            action = "Create Notification",
            entity = "Notification",
            description = "Notification created successfully"
    )
    public NotificationDTO save(NotificationRequest request)
    {
        var user = m_userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        if (request.getSentAt().isBefore(LocalDateTime.now()))
            throw new ApiException(MyError.INVALID_NOTIFICATION_TIME);

        var notification = m_notificationMapper.toNotification(request);

        if (request.getType() == NotificationType.EMAIL)
            notification.setToAddress(user.getEmail());
        else if (request.getType() == NotificationType.SMS)
            notification.setToAddress(user.getProfile().getPhone());
        else
            throw new ApiException(MyError.INVALID_NOTIFICATION_TYPE);

        notification.setStatus(NotificationStatus.PENDING);
        return m_notificationMapper.toNotificationDTO(m_notificationRepository.save(notification));

    }

    @Auditable(
            action = "Find Notification by ID",
            entity = "Notification",
            description = "Notification found successfully"
    )
    public NotificationDTO findNotificationById(Long id)
    {
        var notification = m_notificationRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.NOTIFICATION_NOT_FOUND));

        return m_notificationMapper.toNotificationDTO(notification);
    }

    @Auditable(
            action = "Find Notification by Status",
            entity = "Notification",
            description = "Notification found successfully by status"
    )
    public List<NotificationDTO> findAllNotificationsByStatus(String status)
    {
        try {
            var notificationStatus = NotificationStatus.valueOf(status.toUpperCase(Locale.ENGLISH));
            return m_notificationMapper.toNotificationDTOs(m_notificationRepository.findAllByStatus(notificationStatus));
        }
        catch (Exception ignored) {
            throw new ApiException(MyError.INVALID_NOTIFICATION_STATUS, status);
        }
    }

    @Auditable(
            action = "Find Notification by Type",
            entity = "Notification",
            description = "Notification found successfully by type"
    )
    public List<NotificationDTO> findAllNotificationsByType(String type)
    {
        try {
            var notificationType = NotificationType.valueOf(type.toUpperCase(Locale.ENGLISH));
            return m_notificationMapper.toNotificationDTOs(m_notificationRepository.findAllByType(notificationType));
        }
        catch (Exception ignored) {
            throw new ApiException(MyError.INVALID_NOTIFICATION_TYPE, type);
        }
    }

    @Auditable(
            action = "Find Notifications Before Sent At",
            entity = "Notification",
            description = "Notifications found successfully before sent at"
    )
    public List<NotificationDTO> findAllNotificationsBeforeSentAt(LocalDateTime sentAt)
    {
        return m_notificationMapper.toNotificationDTOs(m_notificationRepository.findAllBySentAtBefore(sentAt));
    }

    @Auditable(
            action = "Find Notifications After Sent At",
            entity = "Notification",
            description = "Notifications found successfully after sent at"
    )
    public List<NotificationDTO> findAllNotificationsAfterSentAt(LocalDateTime sentAt)
    {
        return m_notificationMapper.toNotificationDTOs(m_notificationRepository.findAllBySentAtAfter(sentAt));
    }

    @Auditable(
            action = "Find All Notifications",
            entity = "Notification",
            description = "All notifications found successfully"
    )
    public List<NotificationDTO> findAllNotifications()
    {
        return m_notificationMapper.toNotificationDTOs(m_notificationRepository.findAll());
    }

    @Transactional
    @Auditable(
            action = "Delete Notification by ID",
            entity = "Notification",
            description = "Notification deleted successfully by ID"
    )
    public void deleteNotificationById(Long id)
    {
        var notification = m_notificationRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.NOTIFICATION_NOT_FOUND));

        m_notificationRepository.delete(notification);
    }

    @Transactional
    @Auditable(
            action = "Delete All Notifications",
            entity = "Notification",
            description = "All notifications deleted successfully"
    )
    public void deleteAllNotifications()
    {
        m_notificationRepository.deleteAll();
    }
}
