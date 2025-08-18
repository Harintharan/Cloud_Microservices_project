package service_microservice.example.service.offering.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service_microservice.example.service.offering.model.ServiceOffering;
import service_microservice.example.service.offering.service.ServiceOfferingService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service-offering")
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;


    @GetMapping("/saloon/{saloonId}")
    public ResponseEntity<Set<ServiceOffering>> getServiceOfferingsBySaloonId(@PathVariable Long saloonId, @RequestParam(required = false) Long categaryId){

        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySaloonId(saloonId,categaryId);

        return  ResponseEntity.ok(serviceOfferings);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceById(@PathVariable Long id) throws Exception {

        ServiceOffering serviceOfferings = serviceOfferingService.getServiceById(id);

        return  ResponseEntity.ok(serviceOfferings);
    }


    @GetMapping("/list/{ids}")

    public ResponseEntity< Set<ServiceOffering>> getServicesById(@PathVariable  Set<Long> ids) throws Exception {

        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getServiceByIds(ids);

        return  ResponseEntity.ok(serviceOfferings);
    }








}
