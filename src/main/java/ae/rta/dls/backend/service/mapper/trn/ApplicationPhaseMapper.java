package ae.rta.dls.backend.service.mapper.trn;

import ae.rta.dls.backend.domain.trn.ApplicationPhase;
import ae.rta.dls.backend.service.dto.trn.ApplicationPhaseDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity ApplicationPhase and its DTO ApplicationPhaseDTO.
 */
@Mapper(componentModel = "spring", uses = {ApplicationMapper.class})
public interface ApplicationPhaseMapper extends EntityMapper<ApplicationPhaseDTO, ApplicationPhase> {

    @Mapping(source = "application.id", target = "applicationId")
    ApplicationPhaseDTO toDto(ApplicationPhase applicationPhase);

    @Mapping(source = "applicationId", target = "application")
    ApplicationPhase toEntity(ApplicationPhaseDTO applicationPhaseDTO);

    default ApplicationPhase fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationPhase applicationPhase = new ApplicationPhase();
        applicationPhase.setId(id);
        return applicationPhase;
    }
}
