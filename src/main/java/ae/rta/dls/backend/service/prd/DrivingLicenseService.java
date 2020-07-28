package ae.rta.dls.backend.service.prd;

import ae.rta.dls.backend.service.dto.prd.DrivingLicenseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * Service Interface for managing DrivingLicense.
 */
public interface DrivingLicenseService {

    /**
     * Save a drivingLicense.
     *
     * @param drivingLicenseDTO the entity to save
     * @return the persisted entity
     */
    DrivingLicenseDTO save(DrivingLicenseDTO drivingLicenseDTO);

    /**
     * Get all the drivingLicenses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DrivingLicenseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" drivingLicense.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DrivingLicenseDTO> findOne(Long id);

	/**
     * Get the "id" drivingLicense.
     *
     * @param foreignLicenseNo
     * @param issuedFromCountryCode
     * @return the entity
     */
    Optional<DrivingLicenseDTO> findOne(String foreignLicenseNo , String issuedFromCountryCode);

    /**
     * Get the "id" drivingLicense.
     *
     * @param trafficCodeNo
     * @return the entity
     */
    Optional<DrivingLicenseDTO> findOne(String trafficCodeNo);

    /**
     * Get the entity by license number and issue date
     * @param licenseNo
     * @param licenseIssueDate
     *
     * @return Optional<DrivingLicenseDTO>
     */
    Optional<DrivingLicenseDTO> findOneByLicenseNoAndIssueDate(String licenseNo, String licenseIssueDate);

    /**
     * Delete the "id" drivingLicense.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
