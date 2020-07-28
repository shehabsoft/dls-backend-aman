package ae.rta.dls.backend.service.mapper.sys;

import ae.rta.dls.backend.domain.sys.ApplicationConfiguration;
import ae.rta.dls.backend.service.dto.sys.ApplicationConfigurationDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity ApplicationConfiguration and its DTO ApplicationConfigurationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationConfigurationMapper extends EntityMapper<ApplicationConfigurationDTO, ApplicationConfiguration> {



    default ApplicationConfiguration fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
        applicationConfiguration.setId(id);
        return applicationConfiguration;
    }
}
