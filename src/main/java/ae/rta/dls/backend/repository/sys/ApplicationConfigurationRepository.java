package ae.rta.dls.backend.repository.sys;

import org.springframework.cache.annotation.Cacheable;
import ae.rta.dls.backend.domain.sys.ApplicationConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ApplicationConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationConfigurationRepository extends JpaRepository<ApplicationConfiguration, Long> {

    enum ApplicationConfigurationCashe{
        GET_ALL_APPLICATION_CONFIGURATION;
        public static final String APPLICATION_CONFIGURATION_BY_CONFIG_KEY = "applicationConfigurationByConfigKey";
    }

    @Cacheable(cacheNames = ApplicationConfigurationCashe.APPLICATION_CONFIGURATION_BY_CONFIG_KEY, key = "#p0", unless="#result == null or #result.cached == false")
    Optional<ApplicationConfiguration> getByConfigKey(String key);

    boolean existsByConfigKey(String configKey);
}
