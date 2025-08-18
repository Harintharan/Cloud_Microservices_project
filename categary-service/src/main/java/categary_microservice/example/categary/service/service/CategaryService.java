package categary_microservice.example.categary.service.service;

import categary_microservice.example.categary.service.dto.SaloonDTO;
import categary_microservice.example.categary.service.model.Categary;

import java.util.Set;

public interface CategaryService {

    Categary saveCategary(Categary categary , SaloonDTO saloonDTO);
    Set<Categary> getAllCategariesBySaloonId(Long id);

    Categary getCategaryById(Long id) throws Exception;
    Categary deleteCategaryById(Long id, Long saloonId) throws Exception;

    Categary findByIdAndSaloonId(Long id, Long saloonId) throws Exception;






}
