package com.microService.notifications.service.impl;

import com.microService.notifications.mapper.NotificationMapper;
import com.microService.notifications.model.Notification;
import com.microService.notifications.payload.dto.BookingDTO;
import com.microService.notifications.payload.dto.NotificationDTO;
import com.microService.notifications.repository.NotificationRepository;
import com.microService.notifications.service.NotificationService;
import com.microService.notifications.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final BookingFeignClient bookingFeignClient;

    @Override
    public NotificationDTO createNotification(Notification notification) throws Exception {

        Notification savedNotification = notificationRepository.save(notification);
        BookingDTO bookingDTO = bookingFeignClient.getBookingById(savedNotification.getBookingId()).getBody();

        NotificationDTO notificationDTO = NotificationMapper.toDTO(savedNotification,bookingDTO);
        return notificationDTO;
    }

    @Override
    public List<Notification> getAllNotificationByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getAllNotificationBySaloonId(Long saloonId) {
        return notificationRepository.findBySaloonId(saloonId);
    }

    @Override
    public Notification markNotificationAsRead(Long notificationId) throws Exception {
        return notificationRepository.findById(notificationId).map(notification -> {notification.setIsRead(true);
        return notificationRepository.save(notification);
        }).orElseThrow(()->new Exception("Notification not found"));
    }
}
