package ae.rta.dls.backend.service.mapper.trf;

import ae.rta.dls.backend.domain.trf.LicenseApplicationView;
import ae.rta.dls.backend.service.dto.trf.LicenseApplicationViewDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LicenseApplicationView} and its DTO {@link LicenseApplicationViewDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LicenseApplicationViewMapper extends EntityMapper<LicenseApplicationViewDTO, LicenseApplicationView> {



    default LicenseApplicationView fromId(Long id) {
        if (id == null) {
            return null;
        }
        LicenseApplicationView licenseApplicationView = new LicenseApplicationView();
        licenseApplicationView.setId(id);
        return licenseApplicationView;
    }
}
