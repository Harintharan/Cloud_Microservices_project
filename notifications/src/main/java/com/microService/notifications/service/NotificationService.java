package com.microService.notifications.service;

import com.microService.notifications.model.Notification;
import com.microService.notifications.payload.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    NotificationDTO createNotification(Notification notification) throws Exception;
    List<Notification> getAllNotificationByUserId(Long userId);
    List<Notification> getAllNotificationBySaloonId(Long saloonId);
    Notification markNotificationAsRead(Long notificationId) throws Exception;

}
