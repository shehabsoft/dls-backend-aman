package ae.rta.dls.backend.service.mapper.sct;

import ae.rta.dls.backend.domain.sct.ApplicationType;
import ae.rta.dls.backend.service.dto.sct.ApplicationTypeDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity ApplicationType and its DTO ApplicationTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationTypeMapper extends EntityMapper<ApplicationTypeDTO, ApplicationType> {


    @Mapping(target = "services", ignore = true)
    ApplicationType toEntity(ApplicationTypeDTO applicationTypeDTO);

    default ApplicationType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationType applicationType = new ApplicationType();
        applicationType.setId(id);
        return applicationType;
    }
}
