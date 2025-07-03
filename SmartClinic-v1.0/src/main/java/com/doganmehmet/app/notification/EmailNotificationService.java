package com.doganmehmet.app.notification;

import com.doganmehmet.app.entity.Notification;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationService implements INotificationService{
    private final JavaMailSender m_mailSender;
    private final String FROM_ADDRESS = "nikolatesla6347@gmail.com";

    @Override
    public void sendNotification(Notification notification)
    {
        var message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(notification.getToAddress());
        message.setSubject(notification.getSubject());
        message.setText(notification.getMessage());

        try {
            m_mailSender.send(message);
        }
        catch (Exception ignored) {
            throw new ApiException(MyError.MAIL_SEND_FAILED);
        }
    }
}
