package com.doganmehmet.app.notification;

import com.doganmehmet.app.entity.Notification;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.messages.TextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsNotificationService implements INotificationService {

    private final VonageClient m_client;

    @Override
    public void sendNotification(Notification notification)
    {
        var from = "Smart Clinic App";
        var to = notification.getToAddress();
        var message = notification.getMessage();

        var textMessage = new TextMessage(from, to, message);

        try {
            var response = m_client.getSmsClient().submitMessage(textMessage);

            if (response.getMessages().get(0).getStatus() != MessageStatus.OK)
                throw new ApiException(MyError.SMS_SEND_FAILED);
        }
        catch (Exception ignored) {
            throw new ApiException(MyError.SMS_SEND_FAILED);
        }
    }
}
