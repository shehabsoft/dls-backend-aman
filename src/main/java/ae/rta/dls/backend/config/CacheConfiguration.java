package ae.rta.dls.backend.config;

import java.time.Duration;

import ae.rta.dls.backend.domain.prd.BusinessProfile;
import ae.rta.dls.backend.domain.prd.DrivingLicense;
import ae.rta.dls.backend.domain.prd.LearningFile;
import ae.rta.dls.backend.domain.prd.MedicalFitness;
import ae.rta.dls.backend.domain.sdm.GlobalLicenseCategory;
import ae.rta.dls.backend.domain.trf.DrivingLicenseView;
import ae.rta.dls.backend.domain.trf.ExamTraining;
import ae.rta.dls.backend.domain.trf.ForeignLicenseTemplateView;
import ae.rta.dls.backend.domain.trf.LicenseApplicationView;
import ae.rta.dls.backend.domain.ws.rest.BrmRestLog;
import ae.rta.dls.backend.domain.sdm.LicenseCategory;
import ae.rta.dls.backend.domain.sys.*;
import ae.rta.dls.backend.domain.EntityAuditEvent;
import ae.rta.dls.backend.domain.sct.ApplicationType;
import ae.rta.dls.backend.domain.sct.Service;
import ae.rta.dls.backend.domain.sct.ServiceGroup;
import ae.rta.dls.backend.domain.trn.*;
import ae.rta.dls.backend.domain.ws.rest.RestLog;
import ae.rta.dls.backend.repository.sct.ApplicationTypeRepository;
import ae.rta.dls.backend.repository.sdm.LicenseCategoryRepository;
import ae.rta.dls.backend.repository.sct.ServiceRepository;
import ae.rta.dls.backend.repository.sys.*;
import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(ErrorLog.class.getName(), jcacheConfiguration);
            cm.createCache(EntityAuditEvent.class.getName(), jcacheConfiguration);
            cm.createCache(EntityAuditConfigurationRepository.EntityAuditConfigurationCache.AUDIT_CONFIGURATION_BY_ENTITY_NAME, jcacheConfiguration);
            cm.createCache(EntityAuditConfiguration.class.getName(), jcacheConfiguration);
            cm.createCache(ApplicationConfiguration.class.getName(), jcacheConfiguration);
            cm.createCache(ApplicationConfigurationRepository.ApplicationConfigurationCashe.GET_ALL_APPLICATION_CONFIGURATION.APPLICATION_CONFIGURATION_BY_CONFIG_KEY, jcacheConfiguration);
            cm.createCache(DomainRepository.DomainCache.DOMAIN_BY_CODE, jcacheConfiguration);
            cm.createCache(Domain.class.getName(), jcacheConfiguration);
            cm.createCache(DomainValue.class.getName(), jcacheConfiguration);
            cm.createCache(DomainValueRepository.DomainValueCache.DOMAIN_VALUE_BY_DOMAIN_ID, jcacheConfiguration);
            cm.createCache(Domain.class.getName()+ ".domainValues", jcacheConfiguration);
            cm.createCache(RestLog.class.getName(), jcacheConfiguration);
            cm.createCache(AutomatedJobAudit.class.getName(), jcacheConfiguration);
            cm.createCache(AutomatedJobConfig.class.getName(), jcacheConfiguration);
            cm.createCache(AutomatedJobConfigRepository.AutomatedJobConfigCache.AUTOMATED_JOB_BY_JOB, jcacheConfiguration);
            cm.createCache(Service.class.getName(), jcacheConfiguration);
            cm.createCache(ServiceRepository.ServiceCache.GET_SERVICE_BY_CODE, jcacheConfiguration);
            cm.createCache(ServiceRepository.ServiceCache.GET_ACTIVE_SERVICES, jcacheConfiguration);
            cm.createCache(ApplicationType.class.getName(), jcacheConfiguration);
            cm.createCache(ApplicationType.class.getName() + ".services", jcacheConfiguration);
            cm.createCache(ApplicationTypeRepository.ApplicationTypeCache.GET_ACTIVE_APPLICATION_TYPES, jcacheConfiguration);
            cm.createCache(ServiceGroup.class.getName(), jcacheConfiguration);
            cm.createCache(ServiceGroup.class.getName() + ".services", jcacheConfiguration);
            cm.createCache(LicenseCategory.class.getName(), jcacheConfiguration);
            cm.createCache(LicenseCategoryRepository.LicenseCategoryCache.GET_ACTIVE_LICENSE_CATEGORIES_BY_COUNTRY_CODE, jcacheConfiguration);
            cm.createCache(GlobalLicenseCategory.class.getName(), jcacheConfiguration);
            cm.createCache(GlobalLicenseCategory.class.getName()+ ".licenseCategories", jcacheConfiguration);
            cm.createCache(BrmRestLog.class.getName(), jcacheConfiguration);
            cm.createCache(Application.class.getName(), jcacheConfiguration);
            cm.createCache(Application.class.getName() + ".applicationPhases", jcacheConfiguration);
            cm.createCache(Application.class.getName() + ".serviceRequests", jcacheConfiguration);
            cm.createCache(Application.class.getName() + ".applicationViolations", jcacheConfiguration);
            cm.createCache(ApplicationPhase.class.getName(), jcacheConfiguration);
            cm.createCache(ServiceRequest.class.getName(), jcacheConfiguration);
            cm.createCache(ServiceRequest.class.getName() + ".applicationViolations", jcacheConfiguration);
            cm.createCache(ApplicationViolation.class.getName(), jcacheConfiguration);
            cm.createCache(LearningFile.class.getName(), jcacheConfiguration);
            cm.createCache(DrivingLicense.class.getName(), jcacheConfiguration);
            cm.createCache(MedicalFitness.class.getName(), jcacheConfiguration);
            cm.createCache(MimeType.class.getName(), jcacheConfiguration);
            cm.createCache(MimeTypeRepository.MimeTypeCache.GET_MIME_TYPE_BY_EXTENSION, jcacheConfiguration);
            cm.createCache(ExamTraining.class.getName(), jcacheConfiguration);
            cm.createCache(DrivingLicenseView.class.getName(), jcacheConfiguration);
            cm.createCache(LicenseCategoryRepository.LicenseCategoryCache.GET_EXCHANGEABLE_LICENSE_CATEGORIES_BY_COUNTRY_CODE_CACHE, jcacheConfiguration);
            cm.createCache(LicenseCategoryRepository.LicenseCategoryCache.GET_EXCHANGEABLE_LICENSE_CATEGORIES_BY_CITY_CODE_CACHE, jcacheConfiguration);
            cm.createCache(BusinessProfile.class.getName(), jcacheConfiguration);
            cm.createCache(ForeignLicenseTemplateView.class.getName(), jcacheConfiguration);
            cm.createCache(LicenseApplicationView.class.getName(), jcacheConfiguration);
            // PLEASE DON'T REMOVE THIS LINES TO AVOID ANY LAKE OF ENTITY GENERATION
            // jhipster-needle-ehcache-add-entry
        };
    }
}
