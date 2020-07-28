package ae.rta.dls.backend.service.trf.impl;

import ae.rta.dls.backend.service.trf.LicenseApplicationViewService;
import ae.rta.dls.backend.domain.trf.LicenseApplicationView;
import ae.rta.dls.backend.repository.trf.LicenseApplicationViewRepository;
import ae.rta.dls.backend.service.dto.trf.LicenseApplicationViewDTO;
import ae.rta.dls.backend.service.mapper.trf.LicenseApplicationViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LicenseApplicationView}.
 */
@Service
@Transactional
public class LicenseApplicationViewServiceImpl implements LicenseApplicationViewService {

    private static final Integer TRF_LICENSE_APPLICATION_STATUS_UNDERPROCESSING = 1;

    private final Logger log = LoggerFactory.getLogger(LicenseApplicationViewServiceImpl.class);

    private final LicenseApplicationViewRepository licenseApplicationViewRepository;

    private final LicenseApplicationViewMapper licenseApplicationViewMapper;

    public LicenseApplicationViewServiceImpl(LicenseApplicationViewRepository licenseApplicationViewRepository, LicenseApplicationViewMapper licenseApplicationViewMapper) {
        this.licenseApplicationViewRepository = licenseApplicationViewRepository;
        this.licenseApplicationViewMapper = licenseApplicationViewMapper;
    }


    /**
     * Get one underProcessing License Application by emirates Id.
     *
     * @param emiratesId emirates id.
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LicenseApplicationViewDTO> findUnderProcessingLicenseApplicationByEmiratesId(String emiratesId) {

        String debugMsg = String.format("Request to get LicenseApplicationView by emirates id : %s", emiratesId);
        log.debug(debugMsg);

        return licenseApplicationViewRepository.findByEmiratesIdAndStatus(emiratesId, TRF_LICENSE_APPLICATION_STATUS_UNDERPROCESSING)
            .map(licenseApplicationViewMapper::toDto);
    }
}
