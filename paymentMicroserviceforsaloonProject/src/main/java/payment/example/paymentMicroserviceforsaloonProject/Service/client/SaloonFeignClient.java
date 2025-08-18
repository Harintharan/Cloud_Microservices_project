package payment.example.paymentMicroserviceforsaloonProject.Service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import payment.example.paymentMicroserviceforsaloonProject.payload.dto.SaloonDTO;


@FeignClient("SALOON-SERVICE")
public interface SaloonFeignClient {

    @GetMapping("/api/saloons/owner")
    public ResponseEntity<SaloonDTO> getSaloonByOwnerId (@RequestHeader("Authorization") String jwt) throws Exception;

    @GetMapping("/api/saloons/{saloonId}")
    public ResponseEntity<SaloonDTO> getSaloonById ( @PathVariable Long saloonId) throws Exception;


}
