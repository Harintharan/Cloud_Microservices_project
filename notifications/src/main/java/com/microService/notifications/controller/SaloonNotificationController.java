package com.microService.notifications.controller;

import com.microService.notifications.mapper.NotificationMapper;
import com.microService.notifications.model.Notification;
import com.microService.notifications.payload.dto.BookingDTO;
import com.microService.notifications.payload.dto.NotificationDTO;
import com.microService.notifications.service.NotificationService;
import com.microService.notifications.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications/saloon-owner")
public class SaloonNotificationController {

    private final NotificationService notificationService;
    private final BookingFeignClient bookingFeignClient;





    @GetMapping("/saloon/{saloonId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationBySaloonId(@PathVariable Long saloonId){

        List<Notification> notifications = notificationService.getAllNotificationBySaloonId(saloonId);

        List<NotificationDTO>notificationDTOS = notifications.stream().
                map((notification -> {
                    BookingDTO bookingDTO = null;
                    try {
                        bookingDTO = bookingFeignClient.getBookingById(notification.getBookingId()).getBody();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    return NotificationMapper.toDTO(notification,bookingDTO);
                })).collect(Collectors.toList());


        return ResponseEntity.ok(notificationDTOS);
    }

}
