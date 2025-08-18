//package saloon_microservice.example.saloon.service.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import saloon_microservice.example.saloon.service.model.Saloon;
//
//import java.util.List;
//
//public interface SaloonRepository extends JpaRepository<Saloon,Long> {
//
//    Saloon findByOwnerId(Long id);
//
//
//    @Query(
//            "select s from Saloon s where" +
//                    "(lower(s.city) like lower(concat('%',:keyword,'%')) OR +" + " + (lower(s.name) like lower(concat('%',:keyword,'%')) OR +  )"
//
//    )
//    List<Saloon> searchSaloons(@Param("keyword") String keyword);
//
//
//
//
//
//}


package saloon_microservice.example.saloon.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import saloon_microservice.example.saloon.service.model.Saloon;

import java.util.List;
import java.util.Optional;

public interface SaloonRepository extends JpaRepository<Saloon, Long> {

//    Saloon findByOwnerId(Long id);

    Optional<Saloon> findByOwnerId(Long id);

    @Query(
            "SELECT s FROM Saloon s WHERE " +
                    "LOWER(s.city) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%'))"


    )
    List<Saloon> searchSaloons(@Param("keyword") String keyword);

}
