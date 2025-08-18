package Bookingmicroservice.example.Bookingservice.model;

import Bookingmicroservice.example.Bookingservice.domain.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Long saloonId;
    private Long customerId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ElementCollection
    private Set<Long>  serviceIds;


    private BookingStatus status = BookingStatus.PENDING;
    private int totalPrice;


}
