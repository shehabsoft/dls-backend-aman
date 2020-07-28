package ae.rta.dls.backend.service.trf.impl;

import ae.rta.dls.backend.service.trf.DrivingLicenseViewService;
import ae.rta.dls.backend.repository.trf.DrivingLicenseViewRepository;
import ae.rta.dls.backend.service.dto.trf.DrivingLicenseViewDTO;
import ae.rta.dls.backend.service.mapper.trf.DrivingLicenseViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Service Implementation for managing DrivingLicenseView.
 */
@Service
@Transactional
public class DrivingLicenseViewServiceImpl implements DrivingLicenseViewService {

    private static final Integer TRF_LICENSE_STATUS_CANCELED = 8;

    private final Logger log = LoggerFactory.getLogger(DrivingLicenseViewServiceImpl.class);

    private final DrivingLicenseViewRepository drivingLicenseViewRepository;

    private final DrivingLicenseViewMapper drivingLicenseViewMapper;

    public DrivingLicenseViewServiceImpl(DrivingLicenseViewRepository drivingLicenseViewRepository, DrivingLicenseViewMapper drivingLicenseViewMapper) {
        this.drivingLicenseViewRepository = drivingLicenseViewRepository;
        this.drivingLicenseViewMapper = drivingLicenseViewMapper;
    }


    /**
     * Get one drivingLicenseView by emirates Id.
     *
     * @param emiratesId emirates id.
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DrivingLicenseViewDTO> findActiveByEmiratesId(String emiratesId) {
        String debugMsg = String.format("Request to get DrivingLicenseView by emirates id : %s", emiratesId);
        log.debug(debugMsg);
        return drivingLicenseViewRepository.findByEmiratesIdAndStatusIsNot(emiratesId, TRF_LICENSE_STATUS_CANCELED)
            .map(drivingLicenseViewMapper::toDto);
    }
}
