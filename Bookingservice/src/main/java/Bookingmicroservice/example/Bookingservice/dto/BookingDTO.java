package Bookingmicroservice.example.Bookingservice.dto;

import Bookingmicroservice.example.Bookingservice.domain.BookingStatus;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
public class BookingDTO {





    private Long id;


    private Long saloonId;
    private Long customerId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Set<Long> serviceIds;


    private BookingStatus status = BookingStatus.PENDING;
    private int totalPrice;
}
