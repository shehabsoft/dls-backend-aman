package ae.rta.dls.backend.service.mapper.sct;

import ae.rta.dls.backend.domain.sct.Service;
import ae.rta.dls.backend.service.dto.sct.ServiceDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity Service and its DTO ServiceDTO.
 */
@Mapper(componentModel = "spring", uses = {ApplicationTypeMapper.class, ServiceGroupMapper.class})
public interface ServiceMapper extends EntityMapper<ServiceDTO, Service> {

    @Mapping(source = "applicationType.id", target = "applicationTypeId")
    @Mapping(source = "serviceGroup.id", target = "serviceGroupId")
    ServiceDTO toDto(Service service);

    @Mapping(source = "applicationTypeId", target = "applicationType")
    @Mapping(source = "serviceGroupId", target = "serviceGroup")
    Service toEntity(ServiceDTO serviceDTO);

    default Service fromId(Long id) {
        if (id == null) {
            return null;
        }
        Service service = new Service();
        service.setId(id);
        return service;
    }
}
