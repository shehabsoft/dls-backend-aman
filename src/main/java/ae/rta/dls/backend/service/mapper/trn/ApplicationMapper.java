package ae.rta.dls.backend.service.mapper.trn;

import ae.rta.dls.backend.domain.trn.Application;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import ae.rta.dls.backend.service.mapper.sct.ApplicationTypeMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity Application and its DTO ApplicationDTO.
 */
@Mapper(componentModel = "spring", uses = {ApplicationTypeMapper.class})
public interface ApplicationMapper extends EntityMapper<ApplicationDTO, Application> {

    @Mapping(target = "activePhaseDescription", ignore = true)
    @Mapping(source = "applicationType.id", target = "applicationTypeId")
    @Mapping(target = "serviceRequests", ignore = true)
    ApplicationDTO toDto(Application application);

    @Mapping(target = "applicationPhases", ignore = true)
    @Mapping(target = "serviceRequests", ignore = true)
    @Mapping(target = "applicationViolations", ignore = true)
    @Mapping(source = "applicationTypeId", target = "applicationType")
    Application toEntity(ApplicationDTO applicationDTO);

    default Application fromId(Long id) {
        if (id == null) {
            return null;
        }
        Application application = new Application();
        application.setId(id);
        return application;
    }
}
