package saloon_microservice.example.saloon.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import saloon_microservice.example.saloon.service.Mapper.SaloonMapper;
import saloon_microservice.example.saloon.service.model.Saloon;
import saloon_microservice.example.saloon.service.payload.dto.SaloonDTO;
import saloon_microservice.example.saloon.service.payload.dto.UserDTO;
import saloon_microservice.example.saloon.service.service.SaloonService;
import saloon_microservice.example.saloon.service.service.client.UserFeignClient;

import java.util.List;

@RestController
@RequestMapping("/api/saloons")
@RequiredArgsConstructor
public class SaloonController {

    private final SaloonService saloonService;
    private final UserFeignClient userFeignClient;
@PostMapping
    public ResponseEntity<SaloonDTO> createSaloon
        (@RequestBody SaloonDTO saloonDTO,@RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();



        Saloon saloon = saloonService.createSaloon(saloonDTO,userDTO);
        SaloonDTO saloonDTO1 = SaloonMapper.mapToDTO(saloon);





        return ResponseEntity.ok(saloonDTO1);
    }



    @PutMapping("/{saloonId}")
    public ResponseEntity<SaloonDTO> updateSaloon (@RequestBody SaloonDTO saloonDTO , @PathVariable Long saloonId,@RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();



        Saloon saloon = saloonService.updateSaloon(saloonDTO,userDTO,saloonId);
        SaloonDTO saloonDTO1 = SaloonMapper.mapToDTO(saloon);





        return ResponseEntity.ok(saloonDTO1);
    }


    @GetMapping("/{saloonId}")
    public ResponseEntity<SaloonDTO> getSaloonById ( @PathVariable Long saloonId) throws Exception {




        Saloon saloon = saloonService.getSaloonById(saloonId);
        SaloonDTO saloonDTO1 = SaloonMapper.mapToDTO(saloon);





        return ResponseEntity.ok(saloonDTO1);
    }

    @GetMapping
    public ResponseEntity<List<SaloonDTO>> getAllSaloons() {
        List<Saloon> saloons = saloonService.getAllSaloons();

        List<SaloonDTO> saloonDTOS = saloons.stream()
                .map(SaloonMapper::mapToDTO)
                .toList();

        return ResponseEntity.ok(saloonDTOS);
    }


// http://localhost:8083/api/saloons/search?city=batti
    @GetMapping("/search")
    public ResponseEntity<List<SaloonDTO>> searchSaloons (@RequestParam String city) throws Exception {




        List<Saloon> saloons = saloonService.searchSaloonByCityName(city);
        List<SaloonDTO> saloonDTOS = saloons.stream()
                .map(SaloonMapper::mapToDTO)
                .toList();








        return ResponseEntity.ok(saloonDTOS);
    }



    @GetMapping("/owner")
    public ResponseEntity<SaloonDTO> getSaloonByOwnerId (@RequestHeader("Authorization") String jwt) throws Exception {


    UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();
    if(userDTO == null)
    {
        throw new Exception("user not found from jwt");
    }






        Saloon saloon = saloonService.getSaloonByOwnerId(userDTO.getId());
        SaloonDTO saloonDTO1 = SaloonMapper.mapToDTO(saloon);





        return ResponseEntity.ok(saloonDTO1);
    }



}









