package com.doganmehmet.app.dto.request;

import com.doganmehmet.app.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationRequest {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "Subject cannot be blank")
    private String subject;

    @Column(length = 2000)
    @NotBlank(message = "Message cannot be blank")
    private String message;

    @NotNull(message = "Type cannot be null")
    private NotificationType type;

    @NotNull(message = "Time cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime sentAt;
}
