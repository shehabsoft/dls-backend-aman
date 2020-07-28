package ae.rta.dls.backend.service.mapper.sys;

import ae.rta.dls.backend.domain.sys.AutomatedJobConfig;
import ae.rta.dls.backend.service.dto.sys.AutomatedJobConfigDTO;
import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity AutomatedJobConfig and its DTO AutomatedJobConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AutomatedJobConfigMapper extends EntityMapper<AutomatedJobConfigDTO, AutomatedJobConfig> {



    default AutomatedJobConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutomatedJobConfig automatedJobConfig = new AutomatedJobConfig();
        automatedJobConfig.setId(id);
        return automatedJobConfig;
    }
}
