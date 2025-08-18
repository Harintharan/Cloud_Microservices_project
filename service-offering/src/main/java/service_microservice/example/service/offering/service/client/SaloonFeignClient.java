package service_microservice.example.service.offering.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import service_microservice.example.service.offering.dto.SaloonDTO;

@FeignClient("SALOON-SERVICE")
public interface SaloonFeignClient {

    @GetMapping("/api/saloons/owner")
    public ResponseEntity<SaloonDTO> getSaloonByOwnerId (@RequestHeader("Authorization") String jwt) throws Exception;


}
