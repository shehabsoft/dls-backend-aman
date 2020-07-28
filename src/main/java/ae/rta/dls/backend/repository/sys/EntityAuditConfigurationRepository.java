package ae.rta.dls.backend.repository.sys;

import ae.rta.dls.backend.domain.sys.EntityAuditConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the EntityAuditConfiguration entity.
 */
@Repository
public interface EntityAuditConfigurationRepository extends JpaRepository<EntityAuditConfiguration, Long> {

    /**
     * Cache identifiers
     */
    public enum EntityAuditConfigurationCache {
        AUDIT_CONFIGURATION_BY_ENTITY_NAME_CACHE;
        public static final String AUDIT_CONFIGURATION_BY_ENTITY_NAME  = "entityAuditConfigurationByEntityName";
    }

    /**
     * Repository methods
     */



    /**
     * Get the "entityAuditConfiguration" entity by "entityName".
     *
     * @param entityName the id of the entity
     * @return the entity
     */
    @Cacheable(cacheNames = EntityAuditConfigurationCache.AUDIT_CONFIGURATION_BY_ENTITY_NAME)
    Optional<EntityAuditConfiguration> findByEntityName(String entityName);
}
