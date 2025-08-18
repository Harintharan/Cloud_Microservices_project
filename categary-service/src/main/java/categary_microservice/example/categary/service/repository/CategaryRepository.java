package categary_microservice.example.categary.service.repository;

import categary_microservice.example.categary.service.model.Categary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CategaryRepository extends JpaRepository<Categary,Long> {

Set<Categary> findBySaloonId(Long saloonId);
Categary findByIdAndSaloonId(Long id,Long saloonId);
}
