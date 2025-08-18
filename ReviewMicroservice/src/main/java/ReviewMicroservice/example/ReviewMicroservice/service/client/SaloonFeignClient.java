package ReviewMicroservice.example.ReviewMicroservice.service.client;

import com.microService.notifications.payload.dto.SaloonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient("SALOON-SERVICE")
public interface SaloonFeignClient {

    @GetMapping("/api/saloons/owner")
    public ResponseEntity<SaloonDTO> getSaloonByOwnerId (@RequestHeader("Authorization") String jwt) throws Exception;
@GetMapping("/api/saloons/{saloonId}")
    public ResponseEntity<SaloonDTO> getSaloonById ( @PathVariable Long saloonId) throws Exception;


}
