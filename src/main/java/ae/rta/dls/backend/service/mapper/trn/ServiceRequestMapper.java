package ae.rta.dls.backend.service.mapper.trn;

import ae.rta.dls.backend.domain.trn.ServiceRequest;
import ae.rta.dls.backend.service.dto.trn.ServiceRequestDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import ae.rta.dls.backend.service.mapper.sct.ServiceMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity ServiceRequest and its DTO ServiceRequestDTO.
 */
@Mapper(componentModel = "spring", uses = {ApplicationMapper.class, ServiceMapper.class})
public interface ServiceRequestMapper extends EntityMapper<ServiceRequestDTO, ServiceRequest> {

    @Mapping(source = "application.id", target = "applicationId")
    @Mapping(source = "reversedBy.id", target = "reversedById")
    @Mapping(source = "application", target = "applicationDetails", ignore = true )
    ServiceRequestDTO toDto(ServiceRequest serviceRequest);

    @Mapping(source = "applicationId", target = "application")
    @Mapping(target = "applicationViolations", ignore = true)
    @Mapping(source = "reversedById", target = "reversedBy")
    ServiceRequest toEntity(ServiceRequestDTO serviceRequestDTO);

    default ServiceRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setId(id);
        return serviceRequest;
    }
}
