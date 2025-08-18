package Bookingmicroservice.example.Bookingservice.model;

import lombok.Data;

@Data
public class SaloonReport {


    private Long saloonId;
    private String saloonName;
    private Integer totalEarnings;
    private Integer totalBookings;
    private Integer cancelledBookings;
    private Double totalRefund;


}
