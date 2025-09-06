package com.microService.notifications.payload.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class NotificationDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private String description;
    private Boolean isRead=false;
    private Long userId;
    private Long saloonId;
    private LocalDateTime createdAt;
    private Long bookingId;

    private BookingDTO booking;


}
