package ae.rta.dls.backend.repository.prd;

import ae.rta.dls.backend.domain.prd.LearningFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the LearningFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LearningFileRepository extends JpaRepository<LearningFile, Long> {

    /**
     * Find active learning file by application id
     *
     * @param applicationId application id.
     * @return the entity
     */
    @Query(value =
        "SELECT lefi.*" +
            "  FROM PRD_LEARNING_FILE lefi,TRN_APPLICATION APPL" +
            " WHERE APPL.ID = lefi.APPLICATION_ID" +
            "   AND APPL.ID = :applicationId"+
            "   AND LEFI.PRODUCT_DOCUMENT -> 'learningFileDetails' ->> 'status'  IN ('PENDING_FOR_EYE_TEST','PENDING_FOR_MEDICAL_ASSESSMENT','UNDER_PROCESSING')"
        ,nativeQuery = true)
    Optional<LearningFile> findActiveLearningFileByApplicationId(@Param("applicationId") String applicationId);


    /**
     * Find active learning file by emirates id
     *
     * @param emiratesId emirates Id.
     * @return the entity
     */
    @Query(value =
        "SELECT lefi.*" +
            "  FROM PRD_LEARNING_FILE lefi" +
        " WHERE LEFI.PRODUCT_DOCUMENT -> 'customerInfo' ->> 'emiratesId' = :emiratesId" +
            "   AND LEFI.PRODUCT_DOCUMENT -> 'learningFileDetails' - >> 'status'  IN ('PENDING_FOR_EYE_TEST','PENDING_FOR_MEDICAL_ASSESSMENT','UNDER_PROCESSING')"
        ,nativeQuery = true)
    Optional<LearningFile> findActiveLearningFileByEmiratesId(@Param("emiratesId") String emiratesId);

    /**
     * Find active learning file by Application Id
     *
     * @param applicationId Application Id
     * @return the entity
     */
    Optional<LearningFile> findByApplicationId(Long applicationId);

    /**
     * Find active learning file by Traffic Code No
     *
     * @param trafficCodeNo Traffic Code No
     * @return the entity
     */
    @Query(value =
        "SELECT lefi.*" +
            "  FROM PRD_LEARNING_FILE lefi" +
            " WHERE JSON_VALUE(LEFI.PRODUCT_DOCUMENT, '$.customerInfo.trafficCodeNo' RETURNING NUMBER(15)) = :trafficCodeNo" +
            "   AND JSON_VALUE(LEFI.PRODUCT_DOCUMENT, '$.learningFileDetails.status' RETURNING VARCHAR2(200)) IN ('PENDING_FOR_EYE_TEST','PENDING_FOR_MEDICAL_ASSESSMENT','UNDER_PROCESSING')"
        ,nativeQuery = true)
    Optional<LearningFile> findActiveLearningFileByTrafficCodeNo(@Param("trafficCodeNo") Long trafficCodeNo);
}
