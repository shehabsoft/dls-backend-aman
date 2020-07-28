package ae.rta.dls.backend.service.prd.impl;

import ae.rta.dls.backend.service.prd.DrivingLicenseService;
import ae.rta.dls.backend.domain.prd.DrivingLicense;
import ae.rta.dls.backend.repository.prd.DrivingLicenseRepository;
import ae.rta.dls.backend.service.dto.prd.DrivingLicenseDTO;
import ae.rta.dls.backend.service.mapper.prd.DrivingLicenseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Service Implementation for managing DrivingLicense.
 */
@Service
@Transactional
public class DrivingLicenseServiceImpl implements DrivingLicenseService {

    private final Logger log = LoggerFactory.getLogger(DrivingLicenseServiceImpl.class);

    private final DrivingLicenseRepository drivingLicenseRepository;

    private final DrivingLicenseMapper drivingLicenseMapper;

    public DrivingLicenseServiceImpl(DrivingLicenseRepository drivingLicenseRepository, DrivingLicenseMapper drivingLicenseMapper) {
        this.drivingLicenseRepository = drivingLicenseRepository;
        this.drivingLicenseMapper = drivingLicenseMapper;
    }

    /**
     * Save a drivingLicense.
     *
     * @param drivingLicenseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DrivingLicenseDTO save(DrivingLicenseDTO drivingLicenseDTO) {
        log.debug("Request to save DrivingLicense : {}", drivingLicenseDTO);
        DrivingLicense drivingLicense = drivingLicenseMapper.toEntity(drivingLicenseDTO);
        drivingLicense = drivingLicenseRepository.save(drivingLicense);
        return drivingLicenseMapper.toDto(drivingLicense);
    }

    /**
     * Get all the drivingLicenses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DrivingLicenseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DrivingLicenses");
        return drivingLicenseRepository.findAll(pageable)
            .map(drivingLicenseMapper::toDto);
    }


    /**
     * Get one drivingLicense by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DrivingLicenseDTO> findOne(Long id) {
        log.debug("Request to get DrivingLicense : {}", id);
        return drivingLicenseRepository.findById(id)
            .map(drivingLicenseMapper::toDto);
    }

    /**
     * Get the entity by license number and issue date
     * @param licenseNo
     * @param licenseIssueDate
     *
     * @return Optional<DrivingLicenseDTO>
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DrivingLicenseDTO> findOneByLicenseNoAndIssueDate(String licenseNo, String licenseIssueDate) {
        log.debug("Request to get DrivingLicense : {}", licenseNo, licenseIssueDate);
        return drivingLicenseRepository.findByLicenseNoAndIssueDate(licenseNo, licenseIssueDate)
            .map(drivingLicenseMapper::toDto);
    }

    /**
     * Get the "id" drivingLicense.
     *
     * @param foreignLicenseNo
     * @param issuedFromCountryCode
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DrivingLicenseDTO> findOne(String foreignLicenseNo , String issuedFromCountryCode) {
        log.debug("Request to get DrivingLicense by licenseNo and issuedFromCountryCode : {} , {}", foreignLicenseNo , issuedFromCountryCode);
        return drivingLicenseRepository.findOne(foreignLicenseNo,issuedFromCountryCode)
            .map(drivingLicenseMapper::toDto);
    }

    /**
     * Get the "id" drivingLicense.
     *
     * @param trafficCodeNo
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DrivingLicenseDTO> findOne(String trafficCodeNo) {
        log.debug("Request to get DrivingLicense by trafficCodeNo : {}", trafficCodeNo);
        return drivingLicenseRepository.findOne(trafficCodeNo)
            .map(drivingLicenseMapper::toDto);
    }

    /**
     * Delete the drivingLicense by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DrivingLicense : {}", id);
        drivingLicenseRepository.deleteById(id);
    }
}
