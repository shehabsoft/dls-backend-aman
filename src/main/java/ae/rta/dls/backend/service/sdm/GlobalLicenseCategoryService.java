package ae.rta.dls.backend.service.sdm;

import ae.rta.dls.backend.service.dto.sdm.GlobalLicenseCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing GlobalLicenseCategory.
 */
public interface GlobalLicenseCategoryService {

    /**
     * Save a globalLicenseCategory.
     *
     * @param globalLicenseCategoryDTO the entity to save
     * @return the persisted entity
     */
    GlobalLicenseCategoryDTO save(GlobalLicenseCategoryDTO globalLicenseCategoryDTO);

    /**
     * Get all the globalLicenseCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GlobalLicenseCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" globalLicenseCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GlobalLicenseCategoryDTO> findOne(Long id);

    /**
     * Get the globalLicenseCategory entity.
     *
     * @param code the code of the entity
     * @return the entity
     */
    Optional<GlobalLicenseCategoryDTO> findOne(String code);

    /**
     * Delete the "id" globalLicenseCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
