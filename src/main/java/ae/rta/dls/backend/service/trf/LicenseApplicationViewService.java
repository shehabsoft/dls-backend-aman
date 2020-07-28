package ae.rta.dls.backend.service.trf;

import ae.rta.dls.backend.domain.trf.LicenseApplicationView;
import ae.rta.dls.backend.service.dto.trf.LicenseApplicationViewDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LicenseApplicationView}.
 */
public interface LicenseApplicationViewService {


    /**
     * Get one underProcessing license application by emirates Id.
     *
     * @param emiratesId emirates id.
     * @return License Application View DTO
     */
    Optional<LicenseApplicationViewDTO> findUnderProcessingLicenseApplicationByEmiratesId(String emiratesId);
}
