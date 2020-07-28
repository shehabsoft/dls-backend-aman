package ae.rta.dls.backend.repository.sdm;

import ae.rta.dls.backend.domain.sdm.GlobalLicenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the GlobalLicenseCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GlobalLicenseCategoryRepository extends JpaRepository<GlobalLicenseCategory, Long> {

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
    @EntityGraph(attributePaths = "licenseCategories")
    Page<GlobalLicenseCategory> findAll(Pageable pageable);

    /**
     * Get the globalLicenseCategory entity.
     *
     * @param code the code of the entity
     * @return the entity
     */
    Optional<GlobalLicenseCategory> findByCode(String code);
}
