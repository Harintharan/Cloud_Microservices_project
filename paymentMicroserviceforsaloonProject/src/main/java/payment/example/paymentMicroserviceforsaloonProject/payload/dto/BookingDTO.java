package payment.example.paymentMicroserviceforsaloonProject.payload.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
public class  BookingDTO {





    private Long id;


    private Long saloonId;
    private Long customerId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Set<Long> serviceIds;



    private int totalPrice;
}
