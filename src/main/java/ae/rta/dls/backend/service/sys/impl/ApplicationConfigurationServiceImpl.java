package ae.rta.dls.backend.service.sys.impl;

import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.security.util.CryptoUtil;
import ae.rta.dls.backend.service.mapper.sys.ApplicationConfigurationMapper;
import ae.rta.dls.backend.service.sys.ApplicationConfigurationService;
import ae.rta.dls.backend.domain.sys.ApplicationConfiguration;
import ae.rta.dls.backend.repository.sys.ApplicationConfigurationRepository;
import ae.rta.dls.backend.service.dto.sys.ApplicationConfigurationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.util.Objects;
import java.util.Optional;

/**
 * Service Implementation for managing ApplicationConfiguration.
 */
@Service
@Transactional
public class ApplicationConfigurationServiceImpl implements ApplicationConfigurationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationConfigurationServiceImpl.class);

    private final ApplicationConfigurationRepository applicationConfigurationRepository;

    private final ApplicationConfigurationMapper applicationConfigurationMapper;

    private final CryptoUtil cryptoUtil;

    private final Environment environment;

    private final CacheManager cacheManager;

    public ApplicationConfigurationServiceImpl(ApplicationConfigurationRepository applicationConfigurationRepository, ApplicationConfigurationMapper applicationConfigurationMapper, CryptoUtil cryptoUtil,Environment environment,CacheManager cacheManager) {
        this.applicationConfigurationRepository = applicationConfigurationRepository;
        this.applicationConfigurationMapper = applicationConfigurationMapper;
        this.cryptoUtil = cryptoUtil;
        this.environment = environment;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a applicationConfiguration.
     *
     * @param applicationConfigurationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ApplicationConfigurationDTO save(ApplicationConfigurationDTO applicationConfigurationDTO) {
        log.debug("Request to save ApplicationConfiguration : {}", applicationConfigurationDTO);

        if(applicationConfigurationDTO != null && applicationConfigurationDTO.isEncrypted()) {
            applicationConfigurationDTO.setConfigValue(cryptoUtil.encrypt(applicationConfigurationDTO.getConfigValue()));
        }

        ApplicationConfiguration applicationConfiguration = applicationConfigurationMapper.toEntity(applicationConfigurationDTO);

        // Clear Configuration Caches..
        clearConfigurationCaches(applicationConfiguration);

        applicationConfiguration = applicationConfigurationRepository.save(applicationConfiguration);
        return applicationConfigurationMapper.toDto(applicationConfiguration);
    }

    /**
     * Get all the applicationConfigurations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationConfigurationDTO> findAll(Pageable pageable) throws BadPaddingException, IllegalBlockSizeException {
        log.debug("Request to get all ApplicationConfigurations");
        Page<ApplicationConfigurationDTO> page = applicationConfigurationRepository.findAll(pageable)
                .map(applicationConfigurationMapper::toDto);

        for (ApplicationConfigurationDTO vo : page.getContent()) {
            if(vo.isEncrypted().booleanValue()) {
                vo.setConfigValue(cryptoUtil.decrypt(vo.getConfigValue()));
            }
        }
        return page;
    }

    /**
     * Get one applicationConfiguration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationConfigurationDTO> findOne(Long id) {
        log.debug("Request to get ApplicationConfiguration : {}", id);
        return applicationConfigurationRepository.findById(id)
                .map(applicationConfigurationMapper::toDto);
    }

    /**
     * Delete the applicationConfiguration by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationConfiguration : {}", id);
        Optional<ApplicationConfiguration> applicationConfiguration = applicationConfigurationRepository.findById(id);
        if(applicationConfiguration.isPresent()) {
            // Clear Configuration Caches..
            clearConfigurationCaches(applicationConfiguration.get());
            applicationConfigurationRepository.deleteById(id);
        }
    }

    /**
     * Get the "key" applicationConfiguration.
     *
     * @param key the key of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationConfigurationDTO> getConfiguration(String key) {
        log.debug("Request to get Application Configuration By Name: {}", key);

        String propertyValue = environment.getProperty(key);
        if(!StringUtil.isEmpty(propertyValue)) {
            ApplicationConfigurationDTO appConfiguration = new ApplicationConfigurationDTO();
            appConfiguration.setConfigKey(key);
            appConfiguration.setConfigValue(propertyValue);
            return Optional.of(appConfiguration);
        }

        Optional<ApplicationConfigurationDTO> appConfiguration = applicationConfigurationRepository.getByConfigKey(key)
                .map(applicationConfigurationMapper::toDto);

        if(appConfiguration.isPresent() && appConfiguration.get().isEncrypted().booleanValue()) {
            appConfiguration.get().setConfigValue(
                    cryptoUtil.decrypt(appConfiguration.get().getConfigValue()));
        }
        return appConfiguration;
    }

    /**
     * Clear Configuration Caches..
     *
     * @param applicationConfiguration Application Configuration Entity Instance.
     */
    private void clearConfigurationCaches(ApplicationConfiguration applicationConfiguration) {
        Objects.requireNonNull(cacheManager.getCache(ApplicationConfigurationRepository.ApplicationConfigurationCashe.GET_ALL_APPLICATION_CONFIGURATION.APPLICATION_CONFIGURATION_BY_CONFIG_KEY))
                .evict(applicationConfiguration.getConfigKey());
    }
}
