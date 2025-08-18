package saloon_microservice.example.saloon.service.payload.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;


@Getter
@Setter

public class SaloonDTO {




    private Long id;

    private String name;

    private List<String> images;


    private String address;


    private String phoneNumber;



    private String city;

    private String email;


    private Long ownerId;



    private LocalTime openTime;



    private LocalTime closeTime;


}
