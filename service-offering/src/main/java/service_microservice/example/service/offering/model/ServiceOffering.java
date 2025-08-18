package service_microservice.example.service.offering.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServiceOffering {
@Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
@Column(nullable = false)
private String name;

    @Column(nullable = false)
private String description;

    @Column(nullable = false)
private int price;

    @Column(nullable = false)
private int duration;

    @Column(nullable = false)
    private Long saloonId;



    private String image;


    @Column(nullable = false)
    private Long categaryId;





}
