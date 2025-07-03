package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.NotificationRequest;
import com.doganmehmet.app.dto.response.NotificationDTO;
import com.doganmehmet.app.entity.Notification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(implementationName = "NotificationMapperImpl", componentModel = "spring")
public interface INotificationMapper {

    Notification toNotification(NotificationRequest notificationRequest);

    NotificationDTO toNotificationDTO(Notification notification);

    List<NotificationDTO> toNotificationDTOs(List<Notification> notifications);
}
