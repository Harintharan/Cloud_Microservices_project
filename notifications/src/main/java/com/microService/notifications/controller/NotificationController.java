package com.microService.notifications.controller;

import com.microService.notifications.mapper.NotificationMapper;
import com.microService.notifications.model.Notification;
import com.microService.notifications.payload.dto.BookingDTO;
import com.microService.notifications.payload.dto.NotificationDTO;
import com.microService.notifications.service.NotificationService;
import com.microService.notifications.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final BookingFeignClient bookingFeignClient;
@PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody Notification notification) throws Exception {

        return ResponseEntity.ok(notificationService.createNotification(notification));


    }

@GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationByUserId(@PathVariable Long userId){

    List<Notification> notifications = notificationService.getAllNotificationByUserId(userId);

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

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationDTO> markNotificationAsRead(@PathVariable Long notificationId) throws Exception {

    Notification notification = notificationService.markNotificationAsRead(notificationId);
    BookingDTO bookingDTO = bookingFeignClient.getBookingById(notification.getBookingId()).getBody();


      return ResponseEntity.ok(NotificationMapper.toDTO(notification,bookingDTO));


    }


}
