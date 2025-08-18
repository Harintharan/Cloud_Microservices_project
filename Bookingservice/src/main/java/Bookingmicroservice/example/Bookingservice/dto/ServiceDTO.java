package Bookingmicroservice.example.Bookingservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {


    private  Long id;

    private String name;


    private String description;


    private int price;


    private int duration;


    private Long saloonId;



    private String image;



    private Long categaryId;


}
