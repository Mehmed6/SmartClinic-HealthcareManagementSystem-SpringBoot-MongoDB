package com.doganmehmet.app.dto.response;

import com.doganmehmet.app.enums.NotificationStatus;
import com.doganmehmet.app.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationDTO {
    private String toAddress;
    private String subject;
    private String message;
    private NotificationStatus status;
    private NotificationType type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime sentAt;
}
