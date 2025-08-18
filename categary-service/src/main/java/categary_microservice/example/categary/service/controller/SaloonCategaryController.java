package categary_microservice.example.categary.service.controller;

import categary_microservice.example.categary.service.dto.SaloonDTO;
import categary_microservice.example.categary.service.model.Categary;
import categary_microservice.example.categary.service.service.CategaryService;

import categary_microservice.example.categary.service.service.client.SaloonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categaries/saloon-owner")
public class SaloonCategaryController {



    private final CategaryService categaryService;
    private final SaloonFeignClient saloonFeignClient;


    @PostMapping
    public ResponseEntity<Categary> createCategary(@RequestBody Categary categary,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {

        SaloonDTO saloonDTO = saloonFeignClient.getSaloonByOwnerId(jwt).getBody();



        Categary categary1=  categaryService.saveCategary(categary,saloonDTO);

        return  ResponseEntity.ok(categary1);



    }


    @DeleteMapping("/{id}")

    public ResponseEntity<String> deleteCategary(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {

        SaloonDTO saloonDTO = saloonFeignClient.getSaloonByOwnerId(jwt).getBody();


         categaryService.deleteCategaryById(id,saloonDTO.getId());

         return ResponseEntity.ok("catagery deleted sucessfully");




    }


    @GetMapping("/saloon/{saloonId}/category/{id}")
    public ResponseEntity<Categary>getCatagoriesByIdAndSaloon(@PathVariable Long id,@PathVariable Long saloonId
                                                                  ) throws Exception {
        Categary categary = categaryService.findByIdAndSaloonId(id,saloonId);
        return ResponseEntity.ok(categary);
    }





}
