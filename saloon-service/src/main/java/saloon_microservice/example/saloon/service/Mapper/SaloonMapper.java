package saloon_microservice.example.saloon.service.Mapper;

import saloon_microservice.example.saloon.service.model.Saloon;
import saloon_microservice.example.saloon.service.payload.dto.SaloonDTO;

public class SaloonMapper {

    public static SaloonDTO  mapToDTO(Saloon saloon)

    {

        SaloonDTO saloonDTO = new SaloonDTO();
        saloonDTO.setName(saloon.getName());
        saloonDTO.setCity(saloon.getCity());
        saloonDTO.setImages(saloon.getImages());
        saloonDTO.setOwnerId(saloon.getOwnerId());
        saloonDTO.setAddress(saloon.getAddress());
        saloonDTO.setId(saloon.getId());
        saloonDTO.setEmail(saloon.getEmail());
        saloonDTO.setOpenTime(saloon.getOpenTime());
        saloonDTO.setCloseTime(saloon.getCloseTime());
        saloonDTO.setPhoneNumber(saloon.getPhoneNumber());

        return saloonDTO;

    }

}
