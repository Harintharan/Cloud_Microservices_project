package saloon_microservice.example.saloon.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import saloon_microservice.example.saloon.service.model.Saloon;
import saloon_microservice.example.saloon.service.payload.dto.SaloonDTO;
import saloon_microservice.example.saloon.service.payload.dto.UserDTO;
import saloon_microservice.example.saloon.service.repository.SaloonRepository;
import saloon_microservice.example.saloon.service.service.SaloonService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaloonServiceImpl implements SaloonService {

    private final  SaloonRepository saloonRepository;
    @Override
    public Saloon createSaloon(SaloonDTO req, UserDTO user) {

        Saloon saloon = new Saloon();

        saloon.setName(req.getName());
        saloon.setCity(req.getCity());
        saloon.setImages(req.getImages());
        saloon.setOwnerId(user.getId());
        saloon.setAddress(req.getAddress());
        saloon.setId(req.getId());
        saloon.setEmail(req.getEmail());
        saloon.setOpenTime(req.getOpenTime());
        saloon.setCloseTime(req.getCloseTime());
        saloon.setPhoneNumber(req.getPhoneNumber());


        return saloonRepository.save(saloon);











    }
/// owner of the sallon only can update the saloon
    @Override
    public Saloon updateSaloon(SaloonDTO saloon, UserDTO user, Long saloonId) throws Exception {


        Saloon existingSaloon = saloonRepository.findById(saloonId).orElse(null);
        if(existingSaloon != null && saloon.getOwnerId().equals(user.getId())){

            existingSaloon.setName(saloon.getName());
            existingSaloon.setCity(saloon.getCity());
            existingSaloon.setImages(saloon.getImages());
            existingSaloon.setOwnerId(user.getId());
            existingSaloon.setAddress(saloon.getAddress());

            existingSaloon.setEmail(saloon.getEmail());
            existingSaloon.setOpenTime(saloon.getOpenTime());
            existingSaloon.setCloseTime(saloon.getCloseTime());
            existingSaloon.setPhoneNumber(saloon.getPhoneNumber());


           return saloonRepository.save(existingSaloon);







        }

        throw new Exception("saloon not exist");



    }

    @Override
    public List<Saloon> getAllSaloons() {
     return  saloonRepository.findAll();
    }

    @Override
    public Saloon getSaloonById(Long saloonId) throws Exception {
      Saloon saloon= saloonRepository.findById(saloonId).orElse(null);
      if(saloon== null)
      {

          throw new Exception("saloon not exist");
      }


      return saloon;
    }
//
//    @Override
//    public Saloon getSaloonByOwnerId(Long ownerId) {
//       return saloonRepository.findByOwnerId(ownerId);
//    }

    @Override
    public Saloon getSaloonByOwnerId(Long ownerId) throws Exception {
        return saloonRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("No saloon found for owner id: " + ownerId));
    }


    @Override
    public List<Saloon> searchSaloonByCityName(String city) {
       return saloonRepository.searchSaloons(city);

    }
}
