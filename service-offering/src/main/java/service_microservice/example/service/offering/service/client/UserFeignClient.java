package service_microservice.example.service.offering.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import service_microservice.example.service.offering.dto.UserDTO;


@FeignClient("USER-SERVICE")
public interface UserFeignClient {


    @GetMapping("/api/users/{id}")

    public ResponseEntity<UserDTO>
    getUserById(@PathVariable Long id)throws Exception;


    @GetMapping("/api/users/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception;

}
