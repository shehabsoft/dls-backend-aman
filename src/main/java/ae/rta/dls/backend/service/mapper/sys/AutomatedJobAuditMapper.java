package ae.rta.dls.backend.service.mapper.sys;

import ae.rta.dls.backend.domain.sys.AutomatedJobAudit;
import ae.rta.dls.backend.service.dto.sys.AutomatedJobAuditDTO;
import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity AutomatedJobAudit and its DTO AutomatedJobAuditDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AutomatedJobAuditMapper extends EntityMapper<AutomatedJobAuditDTO, AutomatedJobAudit> {



    default AutomatedJobAudit fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutomatedJobAudit automatedJobAudit = new AutomatedJobAudit();
        automatedJobAudit.setId(id);
        return automatedJobAudit;
    }
}
