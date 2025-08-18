package service_microservice.example.service.offering.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service_microservice.example.service.offering.dto.CategaryDTO;
import service_microservice.example.service.offering.dto.SaloonDTO;
import service_microservice.example.service.offering.dto.ServiceDTO;
import service_microservice.example.service.offering.model.ServiceOffering;
import service_microservice.example.service.offering.repository.ServiceOfferingRepository;
import service_microservice.example.service.offering.service.ServiceOfferingService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class ServiceOfferingServiceImpl implements ServiceOfferingService {


    private final ServiceOfferingRepository serviceOfferingRepository;
    @Override
    public ServiceOffering createService(SaloonDTO saloonDTO, CategaryDTO categaryDTO, ServiceDTO serviceDTO) {

        ServiceOffering serviceOffering = new ServiceOffering();

        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setCategaryId(categaryDTO.getId());
serviceOffering.setDuration(serviceDTO.getDuration());
serviceOffering.setPrice(serviceDTO.getPrice());
serviceOffering.setSaloonId(saloonDTO.getId());

return  serviceOfferingRepository.save(serviceOffering);

    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingRepository.findById(serviceId).orElse(null);

        if(serviceOffering != null)
        {


            serviceOffering.setImage(service.getImage());
            serviceOffering.setName(service.getName());
            serviceOffering.setDescription(service.getDescription());

            serviceOffering.setDuration(service.getDuration());
            serviceOffering.setPrice(service.getPrice());


            return  serviceOfferingRepository.save(serviceOffering);


        }

        throw new Exception("service not exist with id" + serviceId);
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySaloonId(Long saloonId, Long categaryId) {


        Set<ServiceOffering> services=  serviceOfferingRepository.findBySaloonId(saloonId);
        if(categaryId != null)
        {
            services = services.stream().filter((service)->service.getCategaryId() != null && service.getCategaryId() == categaryId).collect(Collectors.toSet());


        }

        return services;


    }

    @Override
    public Set<ServiceOffering> getServiceByIds(Set<Long> ids) {

        List<ServiceOffering> serviceOfferingList= serviceOfferingRepository.findAllById(ids);
        return new HashSet<>(serviceOfferingList);

    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {
        ServiceOffering serviceOffering=  serviceOfferingRepository.findById(id).orElse(null);

        if(serviceOffering != null)
        {
            return serviceOffering;
        }


        throw  new Exception("there is no services related to this id" + id);
    }
}
