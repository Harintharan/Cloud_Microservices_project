package service_microservice.example.service.offering.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service_microservice.example.service.offering.dto.CategaryDTO;
import service_microservice.example.service.offering.dto.SaloonDTO;
import service_microservice.example.service.offering.dto.ServiceDTO;
import service_microservice.example.service.offering.model.ServiceOffering;
import service_microservice.example.service.offering.service.ServiceOfferingService;
import service_microservice.example.service.offering.service.client.CategoryFeignClient;
import service_microservice.example.service.offering.service.client.SaloonFeignClient;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering/saloon-owner")
public class SaloonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;
    private final SaloonFeignClient saloonFeignClient;
    private final CategoryFeignClient categoryFeignClient;


    @PostMapping
    public ResponseEntity<ServiceOffering> createService( @RequestBody ServiceDTO serviceDTO ,@RequestHeader("Authorization") String jwt) throws Exception {

        SaloonDTO saloonDTO = saloonFeignClient.getSaloonByOwnerId(jwt).getBody();
        System.out.println(saloonDTO);



        CategaryDTO categaryDTO = categoryFeignClient.getCatagoriesByIdAndSaloon(serviceDTO.getCategary(),saloonDTO.getId()).getBody();

        categaryDTO.setId(serviceDTO.getCategary());

        ServiceOffering serviceOffering = serviceOfferingService.createService(saloonDTO, categaryDTO, serviceDTO);

        return  ResponseEntity.ok(serviceOffering);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService( @PathVariable Long id ,@RequestBody ServiceOffering serviceOffering) throws Exception {



        ServiceOffering serviceOfferings = serviceOfferingService.updateService(id,serviceOffering);

        return  ResponseEntity.ok(serviceOfferings);
    }
}
