package ae.rta.dls.backend.repository.sys;

import ae.rta.dls.backend.domain.sys.Domain;
import ae.rta.dls.backend.domain.sys.DomainValue;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the DomainValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomainValueRepository extends JpaRepository<DomainValue, Long> {

    /**
     * Enum for the domain value cached methods
     */
    enum DomainValueCache {
        DOMAIN_VALUE_BY_DOMAIN_ID_CACHE;
        public static final String DOMAIN_VALUE_BY_DOMAIN_ID = "findByValueAndDomainId";
    }

    /**
     * Get one domainValue by Value and domain id.
     *
     * @param value the value of the entity
     * @param domainId the Related Domain Id
     * @return the entity
     */
    @Cacheable(cacheNames = DomainValueRepository.DomainValueCache.DOMAIN_VALUE_BY_DOMAIN_ID)
    Optional<DomainValue> findByValueAndDomain_Id(String value, Long domainId);
}
