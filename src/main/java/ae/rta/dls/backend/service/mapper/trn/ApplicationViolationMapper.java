package ae.rta.dls.backend.service.mapper.trn;

import ae.rta.dls.backend.domain.trn.ApplicationViolation;
import ae.rta.dls.backend.service.dto.trn.ApplicationViolationDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity ApplicationViolation and its DTO ApplicationViolationDTO.
 */
@Mapper(componentModel = "spring", uses = {ApplicationMapper.class, ServiceRequestMapper.class})
public interface ApplicationViolationMapper extends EntityMapper<ApplicationViolationDTO, ApplicationViolation> {

    @Mapping(source = "application.id", target = "applicationId")
    @Mapping(source = "serviceRequest.id", target = "serviceRequestId")
    ApplicationViolationDTO toDto(ApplicationViolation applicationViolation);

    @Mapping(source = "applicationId", target = "application")
    @Mapping(source = "serviceRequestId", target = "serviceRequest")
    ApplicationViolation toEntity(ApplicationViolationDTO applicationViolationDTO);

    default ApplicationViolation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationViolation applicationViolation = new ApplicationViolation();
        applicationViolation.setId(id);
        return applicationViolation;
    }
}
