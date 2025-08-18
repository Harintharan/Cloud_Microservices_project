package saloon_microservice.example.saloon.service.service;

import saloon_microservice.example.saloon.service.model.Saloon;
import saloon_microservice.example.saloon.service.payload.dto.SaloonDTO;
import saloon_microservice.example.saloon.service.payload.dto.UserDTO;

import java.util.List;

public interface SaloonService {

    Saloon createSaloon(SaloonDTO saloon, UserDTO user);

    Saloon updateSaloon(SaloonDTO saloon,UserDTO user, Long saloonId) throws Exception;

    List<Saloon> getAllSaloons();
    Saloon getSaloonById(Long saloonId) throws Exception;

    Saloon getSaloonByOwnerId(Long ownerId) throws Exception;

    List<Saloon> searchSaloonByCityName(String city);








}
