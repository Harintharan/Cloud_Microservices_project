package payment.example.paymentMicroserviceforsaloonProject.Service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import payment.example.paymentMicroserviceforsaloonProject.payload.dto.CategaryDTO;


@FeignClient("CATEGORY-SERVICE")
public interface CategoryFeignClient {

//    @GetMapping("/api/categaries/{id}")
//    public ResponseEntity<CategaryDTO> getCategaryById(@PathVariable Long id) throws Exception;

    @GetMapping("/api/categaries/saloon-owner/saloon/{saloonId}/category/{id}")
    public ResponseEntity<CategaryDTO>getCatagoriesByIdAndSaloon(@PathVariable Long id, @PathVariable Long saloonId
    ) throws Exception;
}
