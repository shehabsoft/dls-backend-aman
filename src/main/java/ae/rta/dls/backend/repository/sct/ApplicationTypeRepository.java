package ae.rta.dls.backend.repository.sct;

import ae.rta.dls.backend.domain.enumeration.trn.ApplicationTypeStatus;
import ae.rta.dls.backend.domain.sct.ApplicationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ApplicationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationTypeRepository extends JpaRepository<ApplicationType, Long> {

    enum ApplicationTypeCache {
        GET_ACTIVE_APPLICATION_TYPES_CACHE;
        public static final String GET_ACTIVE_APPLICATION_TYPES = "getActiveApplicationTypes";
    }

    @EntityGraph(attributePaths = "services")
    Optional<ApplicationType> getByCode(Long code);

    @EntityGraph(attributePaths = "services")
    Optional<ApplicationType> findById(Long id);

    @EntityGraph(attributePaths = "services")
    Page<ApplicationType> getApplicationTypeByStatusIsNot(ApplicationTypeStatus status, Pageable pageable);
}
