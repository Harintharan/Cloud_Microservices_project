package categary_microservice.example.categary.service.service.client;

import categary_microservice.example.categary.service.dto.SaloonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient("SALOON-SERVICE")
public interface SaloonFeignClient {

    @GetMapping("/api/saloons/owner")
    public ResponseEntity<SaloonDTO> getSaloonByOwnerId (@RequestHeader("Authorization") String jwt) throws Exception;


}
