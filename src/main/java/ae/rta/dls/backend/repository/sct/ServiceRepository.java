package ae.rta.dls.backend.repository.sct;

import ae.rta.dls.backend.domain.enumeration.trn.ServiceStatus;
import ae.rta.dls.backend.domain.sct.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.Optional;


/**
 * Spring Data  repository for the Service entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    enum ServiceCache {
        GET_ACTIVE_SERVICES_CACHE,
        GET_SERVICE_BY_CODE_CACHE;
        public static final String GET_ACTIVE_SERVICES = "findActiveServices";
        public static final String GET_SERVICE_BY_CODE = "findByCode";
    }

    Page<Service> getServiceByStatusIsNot(ServiceStatus status, Pageable pageable);

    /**
     * Get Service by code.
     *
     * @param code the code of the entity
     * @param status the status of the entity
     * @return the entity
     */
    @Cacheable(cacheNames = ServiceCache.GET_SERVICE_BY_CODE)
    Optional<Service> findByCodeAndStatus(String code, ServiceStatus status);
}
