package ae.rta.dls.backend.service.mapper.sys;

import ae.rta.dls.backend.domain.sys.EntityAuditConfiguration;
import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import ae.rta.dls.backend.service.dto.sys.*;

/**
 * Mapper for the entity EntityAuditConfiguration and its DTO EntityAuditConfigurationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntityAuditConfigurationMapper extends EntityMapper<EntityAuditConfigurationDTO, EntityAuditConfiguration> {

    default EntityAuditConfiguration fromId(Long id) {
        if (id == null) {
            return null;
        }
        EntityAuditConfiguration entityAuditConfiguration = new EntityAuditConfiguration();
        entityAuditConfiguration.setId(id);
        return entityAuditConfiguration;
    }
}
