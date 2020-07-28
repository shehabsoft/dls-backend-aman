package ae.rta.dls.backend.service.mapper.prd;

import ae.rta.dls.backend.domain.prd.DrivingLicense;
import ae.rta.dls.backend.service.dto.prd.DrivingLicenseDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity DrivingLicense and its DTO DrivingLicenseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DrivingLicenseMapper extends EntityMapper<DrivingLicenseDTO, DrivingLicense> {



    default DrivingLicense fromId(Long id) {
        if (id == null) {
            return null;
        }
        DrivingLicense drivingLicense = new DrivingLicense();
        drivingLicense.setId(id);
        return drivingLicense;
    }
}
