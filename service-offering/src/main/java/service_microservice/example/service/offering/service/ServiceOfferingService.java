package service_microservice.example.service.offering.service;

import service_microservice.example.service.offering.dto.CategaryDTO;
import service_microservice.example.service.offering.dto.SaloonDTO;
import service_microservice.example.service.offering.dto.ServiceDTO;
import service_microservice.example.service.offering.model.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SaloonDTO saloonDTO, CategaryDTO categaryDTO, ServiceDTO serviceDTO);

    ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception;

    Set<ServiceOffering> getAllServiceBySaloonId(Long saloonId, Long categaryId);

    Set<ServiceOffering> getServiceByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long id) throws Exception;











}
