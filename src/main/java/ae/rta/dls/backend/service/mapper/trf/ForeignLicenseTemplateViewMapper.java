package ae.rta.dls.backend.service.mapper.trf;

import ae.rta.dls.backend.domain.trf.ForeignLicenseTemplateView;
import ae.rta.dls.backend.service.dto.trf.ForeignLicenseTemplateViewDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity ForeignLicenseTemplateViewMapper and its DTO ForeignLicenseTemplateViewDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ForeignLicenseTemplateViewMapper extends EntityMapper<ForeignLicenseTemplateViewDTO, ForeignLicenseTemplateView> {

    default ForeignLicenseTemplateView fromId(Long id) {
        if (id == null) {
            return null;
        }
        ForeignLicenseTemplateView foreignLicenseTemplateView = new ForeignLicenseTemplateView();
        foreignLicenseTemplateView.setId(id);
        return foreignLicenseTemplateView;
    }
}
