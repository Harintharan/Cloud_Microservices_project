package saloon_microservice.example.saloon.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Saloon {

    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
@Column(nullable = false)
    private String name;
@ElementCollection
private List<String>  images;

@Column(nullable = false)
private String address;

@Column(nullable = false)
private String phoneNumber;
@Column(nullable = false)
private String email;

@Column(nullable = false)

private String city;

@Column(nullable = false)
private Long ownerId;


@Column(nullable = false)
private LocalTime openTime;

@Column(nullable = false)

private LocalTime closeTime;





}
