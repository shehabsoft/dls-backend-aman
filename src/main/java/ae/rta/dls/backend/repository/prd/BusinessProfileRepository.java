package ae.rta.dls.backend.repository.prd;

import ae.rta.dls.backend.domain.prd.BusinessProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the BusinessProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {

    /**
     * Find By Traffic Code No.
     *
     * @param trafficCodeNo Traffic Code No.
     * @return Business Profile DTO.
     */
    @Query(value =
            "SELECT bupr.*" +
            "  FROM prd_business_profile bupr" +
            " WHERE json_value(bupr.product_document, '$.customerInfo.trafficCodeNo' RETURNING NUMBER(15)) = :trafficCodeNo",
            nativeQuery = true)
    Optional<BusinessProfile> findByTrafficCodeNo(@Param("trafficCodeNo") Long trafficCodeNo);

    /**
     * Find By Emirates Id.
     *
     * @param emiratesId Emirates Id.
     * @return Business Profile DTO.
     */
    @Query(value =
            "SELECT bupr.*" +
            "  FROM prd_business_profile bupr" +
            " WHERE json_value(bupr.product_document, '$.customerInfo.emiratesId' RETURNING VARCHAR2(18)) = :emiratesId",
            nativeQuery = true)
    List<BusinessProfile> findByEmiratesId(@Param("emiratesId") String emiratesId);
}
