package ae.rta.dls.backend.repository.sdm;

import ae.rta.dls.backend.domain.enumeration.sdm.LicenseCategoryStatus;
import ae.rta.dls.backend.domain.sdm.LicenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the LicenseCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LicenseCategoryRepository extends JpaRepository<LicenseCategory, Long> {

    enum LicenseCategoryCache {
        GET_ACTIVE_LICENSE_CATEGORIES_BY_COUNTRY_CODE_CACHE;
        public static final String GET_ACTIVE_LICENSE_CATEGORIES_BY_COUNTRY_CODE = "findActiveLicenseCategoriesByCountry";
        public static final String GET_EXCHANGEABLE_LICENSE_CATEGORIES_BY_COUNTRY_CODE_CACHE = "findExchangeableCategoriesByCountry";
        public static final String GET_EXCHANGEABLE_LICENSE_CATEGORIES_BY_CITY_CODE_CACHE = "findExchangeableCategoriesByCity";
    }


    /**
     * Get license category by country code and status
     *
     * @param countryCode country code
     * @param status license category status
     * @param pageable paginated object
     * @return all active license categories related to the given country
     */
    @EntityGraph(attributePaths = "globalLicenseCategory")
    Page<LicenseCategory> getLicenseCategoryByCountryCodeAndStatusIsOrderBySortOrder(String countryCode,
                                                                                LicenseCategoryStatus status,
                                                                                Pageable pageable);

    /**
     * Get exchangeable license category by country code and exchangeable status
     *
     * @param countryCode country code
     * @param exchangeable license category exchangeable
     * @param status license category status
     * @return all active license categories related to the given country
     */
    @EntityGraph(attributePaths = "globalLicenseCategory")
    List<LicenseCategory> getLicenseCategoryByCountryCodeAndExchangeableAndStatusIsOrderByCode(String countryCode,
                                                                                               Boolean exchangeable,
                                                                                               LicenseCategoryStatus status);

    /**
     * Get exchangeable license category by city code and exchangeable status
     *
     * @param cityCode city code
     * @param exchangeable license category exchangeable
     * @param status license category status
     * @return all active license categories related to the given city
     */
    @EntityGraph(attributePaths = "globalLicenseCategory")
    List<LicenseCategory> getLicenseCategoryByCityCodeAndExchangeableAndStatusIsOrderByCode(String cityCode,
                                                                                               Boolean exchangeable,
                                                                                               LicenseCategoryStatus status);

    /**
     * Getter for LicenseCategory entity by passed UTS mapping code
     *
     * @param utsMappingCode
     * @return the entity of LicenseCategory
     */
    Optional<LicenseCategory> getByUtsMappingCode(Integer utsMappingCode);

    /**
     * Getter for LicenseCategory entity by passed code
     *
     * @param code
     * @return the entity of LicenseCategory
     */
    Optional<LicenseCategory> findByCode(String code);
}
