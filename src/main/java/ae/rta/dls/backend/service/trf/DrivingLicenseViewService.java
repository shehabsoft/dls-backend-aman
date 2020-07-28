package ae.rta.dls.backend.service.trf;

import ae.rta.dls.backend.service.dto.trf.DrivingLicenseViewDTO;
import java.util.Optional;

/**
 * Service Interface for managing DrivingLicenseView.
 */
public interface DrivingLicenseViewService {

    /**
     * Get one drivingLicenseView by emirates Id.
     *
     * @param emiratesId emirates id.
     * @return the entity
     */
    Optional<DrivingLicenseViewDTO> findActiveByEmiratesId(String emiratesId);
}
