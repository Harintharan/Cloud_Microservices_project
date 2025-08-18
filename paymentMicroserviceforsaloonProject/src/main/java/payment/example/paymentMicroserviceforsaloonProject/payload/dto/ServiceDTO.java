package payment.example.paymentMicroserviceforsaloonProject.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
