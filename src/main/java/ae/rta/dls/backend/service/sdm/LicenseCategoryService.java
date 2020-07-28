package ae.rta.dls.backend.service.sdm;

import ae.rta.dls.backend.service.dto.sdm.LicenseCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing LicenseCategory.
 */
public interface LicenseCategoryService {

    /**
     * Save a licenseCategory.
     *
     * @param licenseCategoryDTO the entity to save
     * @return the persisted entity
     */
    LicenseCategoryDTO save(LicenseCategoryDTO licenseCategoryDTO);

    /**
     * Get all the licenseCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LicenseCategoryDTO> findAll(Pageable pageable);

    /**
     * Find active license categories by country
     *
     * @param countryCode country code
     * @param pageable the pagination information
     * @return all active license categories related to the given country
     */
    Page<LicenseCategoryDTO> findActiveLicenseCategoriesByCountry(String countryCode, Pageable pageable);

    /**
     * Find exchangeable active license categories  by country
     *
     * @param countryCode country code
     * @return all active license categories related to the given country
     */
    List<LicenseCategoryDTO> findExchangeableCategoriesByCountry(String countryCode);

    /**
     * Find exchangeable active license categories  by city
     *
     * @param cityCode city code
     * @param pageable the pagination information
     * @return all active license categories related to the given city
     */
    List<LicenseCategoryDTO> findExchangeableCategoriesByCity(String cityCode);

    /**
     * Get the "id" licenseCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LicenseCategoryDTO> findOne(Long id);

    /**
     * Deactivate the "id" licenseCategory.
     *
     * @param id the id of the entity
     */
    void deactivate(Long id);

    /**
     * Getter for license category entity by passed UTS mapping code
     *
     * @param utsMappingCode UTS mapping code
     * @return the entity of LicenseCategory
     */
    Optional<LicenseCategoryDTO> findOne(Integer utsMappingCode);

    /**
     * Getter for license category entity by passed code
     *
     * @param code License Category Code
     * @return the entity of LicenseCategory
     */
    Optional<LicenseCategoryDTO> findOne(String code);
}
