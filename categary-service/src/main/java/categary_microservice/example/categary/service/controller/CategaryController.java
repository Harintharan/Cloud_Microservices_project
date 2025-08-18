package categary_microservice.example.categary.service.controller;

import categary_microservice.example.categary.service.model.Categary;
import categary_microservice.example.categary.service.repository.CategaryRepository;
import categary_microservice.example.categary.service.service.CategaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categaries")
public class CategaryController {


    private final CategaryService categaryService;

@GetMapping("/saloon/{id}")
    public ResponseEntity<Set<Categary>> getCategariesBySaloon(@PathVariable Long id)
    {

        Set<Categary> categaries =categaryService.getAllCategariesBySaloonId(id);

        return ResponseEntity.ok(categaries);


    }


    @GetMapping("/{id}")
    public ResponseEntity<Categary> getCategaryById(@PathVariable Long id) throws Exception {
        Categary categary=  categaryService.getCategaryById(id);
        return ResponseEntity.ok(categary);
    }




}
