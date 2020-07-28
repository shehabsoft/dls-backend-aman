package ae.rta.dls.backend.service.mapper.sct;

import ae.rta.dls.backend.domain.sct.ServiceGroup;
import ae.rta.dls.backend.service.dto.sct.ServiceGroupDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity ServiceGroup and its DTO ServiceGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceGroupMapper extends EntityMapper<ServiceGroupDTO, ServiceGroup> {


    @Mapping(target = "services", ignore = true)
    ServiceGroup toEntity(ServiceGroupDTO serviceGroupDTO);

    default ServiceGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceGroup serviceGroup = new ServiceGroup();
        serviceGroup.setId(id);
        return serviceGroup;
    }
}
