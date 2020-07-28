package ae.rta.dls.backend.repository.prd;

import ae.rta.dls.backend.domain.prd.DrivingLicense;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.Optional;

/**
 * Spring Data  repository for the DrivingLicense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrivingLicenseRepository extends JpaRepository<DrivingLicense, Long> {


    @Query(value =
        "SELECT DRLI.* " +
            "    FROM PRD_DRIVING_LICENSE DRLI " +
            "   WHERE JSON_VALUE(DRLI.PRODUCT_DOCUMENT, '$.drivingLicenseDetails.licenseNo' RETURNING VARCHAR2(200)) = :licenseNo" +
            "     AND JSON_VALUE(DRLI.PRODUCT_DOCUMENT, '$.drivingLicenseDetails.issueDate' RETURNING VARCHAR2(200)) = :licenseIssueDate"
        ,nativeQuery = true)
    Optional<DrivingLicense> findByLicenseNoAndIssueDate(@Param("licenseNo") String licenseNo,@Param("licenseIssueDate") String licenseIssueDate);

    /**
     * Get the "id" drivingLicense.
     *
     * @param foreignLicenseNo
     * @param issuedFromCountryCode
     * @return the entity
     */
    @Query(value =
        "SELECT DRLI.*" +
            "  FROM PRD_DRIVING_LICENSE DRLI" +
            " WHERE DRLI.PRODUCT_DOCUMENT -> 'foreignLicenseDetails' ->> 'licenseNo' = :foreignLicenseNo "+
            "   AND DRLI.PRODUCT_DOCUMENT -> 'foreignLicenseDetails' -> 'issuedFromCountryDetails' ->> 'code' = :issuedFromCountryCode" ,nativeQuery = true)
    Optional<DrivingLicense> findOne(@Param("foreignLicenseNo") String foreignLicenseNo,
                                     @Param("issuedFromCountryCode") String issuedFromCountryCode);

    /**
     * Get the "id" drivingLicense.
     *
     * @param trafficCodeNo
     * @return the entity
     */
    @Query(value =
        "SELECT DRLI.*" +
            "  FROM PRD_DRIVING_LICENSE DRLI" +
            " WHERE DRLI.PRODUCT_DOCUMENT -> 'customerInfo' ->> 'trafficCodeNo' = :trafficCodeNo" ,nativeQuery = true)
    Optional<DrivingLicense> findOne(@Param("trafficCodeNo") String trafficCodeNo);
}
