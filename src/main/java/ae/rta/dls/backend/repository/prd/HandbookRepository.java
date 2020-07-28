package ae.rta.dls.backend.repository.prd;

import ae.rta.dls.backend.domain.prd.Handbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Handbook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HandbookRepository extends JpaRepository<Handbook, Long> {

    /**
     * Find by Traffic Code No
     *
     * @param trafficCodeNo Traffic Code No
     * @return the entity
     */
    @Query(value =
        "SELECT habo.*" +
            "  FROM PRD_HAND_BOOK habo" +
            " WHERE JSON_VALUE(habo.PRODUCT_DOCUMENT, '$.customerInfo.trafficCodeNo' RETURNING NUMBER(15)) = :trafficCodeNo"
        ,nativeQuery = true)
    Optional<Handbook> findByTrafficCodeNo(@Param("trafficCodeNo") Long trafficCodeNo);
}
