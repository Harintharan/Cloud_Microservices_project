package service_microservice.example.service.offering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service_microservice.example.service.offering.model.ServiceOffering;

import java.util.List;
import java.util.Set;
@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering,Long> {


    Set<ServiceOffering> findBySaloonId(Long saloonId);



}
