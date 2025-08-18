package com.microService.notifications.messaging;

import com.microService.notifications.controller.NotificationController;
import com.microService.notifications.model.Notification;
import com.microService.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;
@RabbitListener(queues = "notification-queue")
    public void sentNotificationEventConsumer(Notification notification) throws Exception {

        notificationService.createNotification(notification);

    }

}
