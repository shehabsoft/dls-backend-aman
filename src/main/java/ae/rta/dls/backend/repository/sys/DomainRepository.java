package ae.rta.dls.backend.repository.sys;

import ae.rta.dls.backend.domain.sys.Domain;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Domain entity.
 */
@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {

    /**
     * Enum for the domain cached methods
     */
    public enum DomainCache {
        DOMAIN_BY_CODE_CACHE;
        public static final String DOMAIN_BY_CODE = "domainByCode";
    }

    /**
     * Get the "domain" entity by "code".
     *
     * @param code domain code
     * @return the entity
     */
    @EntityGraph(attributePaths = "domainValues")
    @Cacheable(cacheNames = DomainCache.DOMAIN_BY_CODE)
    Optional<Domain> findByCode(String code);
}
