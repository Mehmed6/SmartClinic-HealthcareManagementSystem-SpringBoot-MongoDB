package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.NotificationRequest;
import com.doganmehmet.app.dto.response.NotificationDTO;
import com.doganmehmet.app.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService m_notificationService;

    @PostMapping
    public ResponseEntity<NotificationDTO> saveAndSend(@Valid @RequestBody NotificationRequest request)
    {
        return ResponseEntity.ok(m_notificationService.save(request));
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<NotificationDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(m_notificationService.findNotificationById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationDTO>> findAllByStatus(@PathVariable String status)
    {
        return ResponseEntity.ok(m_notificationService.findAllNotificationsByStatus(status));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationDTO>> findAllByType(@PathVariable String type)
    {
        return ResponseEntity.ok(m_notificationService.findAllNotificationsByType(type));
    }

    @GetMapping("/date/before")
    public ResponseEntity<List<NotificationDTO>> findBeforeSentAt(@RequestParam("sentAt")
                                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime beforeSentAt)
    {
        return ResponseEntity.ok(m_notificationService.findAllNotificationsBeforeSentAt(beforeSentAt));
    }
    @GetMapping("/date/after")
    public ResponseEntity<List<NotificationDTO>> findAfterSentAt(@RequestParam("sentAt")
                                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime afterSentAt)
    {
        return ResponseEntity.ok(m_notificationService.findAllNotificationsAfterSentAt(afterSentAt));
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> findAll()
    {
        return ResponseEntity.ok(m_notificationService.findAllNotifications());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        m_notificationService.deleteNotificationById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll()
    {
        m_notificationService.deleteAllNotifications();
        return ResponseEntity.noContent().build();
    }
}
