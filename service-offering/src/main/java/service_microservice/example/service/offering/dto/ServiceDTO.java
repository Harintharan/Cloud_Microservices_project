package service_microservice.example.service.offering.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDTO {


    private  Long id;

    private String name;


    private String description;


    private int price;


    private int duration;


    private Long saloonId;



    private String image;



    private Long categary;


}
