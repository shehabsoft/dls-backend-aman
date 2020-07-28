package ae.rta.dls.backend.service.mapper.trf;

import ae.rta.dls.backend.domain.trf.DrivingLicenseView;
import ae.rta.dls.backend.service.dto.trf.DrivingLicenseViewDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity DrivingLicenseView and its DTO DrivingLicenseViewDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DrivingLicenseViewMapper extends EntityMapper<DrivingLicenseViewDTO, DrivingLicenseView> {



    default DrivingLicenseView fromId(Long id) {
        if (id == null) {
            return null;
        }
        DrivingLicenseView drivingLicenseView = new DrivingLicenseView();
        drivingLicenseView.setId(id);
        return drivingLicenseView;
    }
}
