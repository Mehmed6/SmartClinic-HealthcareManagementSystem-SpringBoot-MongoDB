package com.doganmehmet.app.notification;

import com.doganmehmet.app.entity.Notification;

public interface INotificationService {

    void sendNotification(Notification notification);
}
