package com.microService.notifications.mapper;

import com.microService.notifications.model.Notification;
import com.microService.notifications.payload.dto.BookingDTO;
import com.microService.notifications.payload.dto.NotificationDTO;

public class NotificationMapper {

    public static NotificationDTO toDTO(Notification notification , BookingDTO bookingDTO){

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setType(notification.getType());
        notificationDTO.setIsRead(notification.getIsRead());
        notificationDTO.setDescription(notification.getDescription());
        notificationDTO.setBookingId(notification.getBookingId());
        notificationDTO.setUserId(notification.getUserId());
        notificationDTO.setSaloonId(notification.getSaloonId());
        notificationDTO.setCreatedAt(notification.getCreatedAt());


        return notificationDTO;



    }
}
