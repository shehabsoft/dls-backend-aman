package ae.rta.dls.backend.service.sdm.impl;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.sdm.LicenseCategoryStatus;
import ae.rta.dls.backend.domain.sdm.LicenseCategory;
import ae.rta.dls.backend.repository.sdm.LicenseCategoryRepository;
import ae.rta.dls.backend.service.dto.sdm.LicenseCategoryDTO;
import ae.rta.dls.backend.service.sdm.LicenseCategoryService;
import ae.rta.dls.backend.service.mapper.sdm.LicenseCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing LicenseCategory.
 */
@Service
@Transactional
public class LicenseCategoryServiceImpl implements LicenseCategoryService {

    private final Logger log = LoggerFactory.getLogger(LicenseCategoryServiceImpl.class);

    private final LicenseCategoryRepository licenseCategoryRepository;

    private final LicenseCategoryMapper licenseCategoryMapper;

    private final CacheManager cacheManager;

    public LicenseCategoryServiceImpl(LicenseCategoryRepository licenseCategoryRepository, LicenseCategoryMapper licenseCategoryMapper,CacheManager cacheManager) {
        this.licenseCategoryRepository = licenseCategoryRepository;
        this.licenseCategoryMapper = licenseCategoryMapper;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a licenseCategory.
     *
     * @param licenseCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LicenseCategoryDTO save(LicenseCategoryDTO licenseCategoryDTO) {
        log.debug("Request to save LicenseCategory : {}", licenseCategoryDTO);

        evictAll();

        LicenseCategory licenseCategory = licenseCategoryMapper.toEntity(licenseCategoryDTO);
        licenseCategory = licenseCategoryRepository.save(licenseCategory);
        return licenseCategoryMapper.toDto(licenseCategory);
    }

    /**
     * Get all the licenseCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LicenseCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LicenseCategories");
        return licenseCategoryRepository.findAll(pageable)
                .map(licenseCategoryMapper::toDto);
    }

    /**
     * Find active license categories by country
     *
     * @param countryCode country code
     * @param pageable the pagination information
     * @return all active license categories related to the given country
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = LicenseCategoryRepository.LicenseCategoryCache.GET_ACTIVE_LICENSE_CATEGORIES_BY_COUNTRY_CODE)
    public Page<LicenseCategoryDTO> findActiveLicenseCategoriesByCountry(String countryCode, Pageable pageable) {
        log.debug("Request to get all active LicenseCategories for the given country");
        return licenseCategoryRepository.getLicenseCategoryByCountryCodeAndStatusIsOrderBySortOrder(StringUtil.upperCase(countryCode),
                                                                                               LicenseCategoryStatus.ACTIVE, pageable)
            .map(licenseCategoryMapper::toDto);
    }

    /**
     * Find exchangeable active license categories by country
     *
     * @param countryCode country code
     * @return all active license categories related to the given country
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = LicenseCategoryRepository.LicenseCategoryCache.GET_EXCHANGEABLE_LICENSE_CATEGORIES_BY_COUNTRY_CODE_CACHE)
    public List<LicenseCategoryDTO> findExchangeableCategoriesByCountry(String countryCode) {
        log.debug("Request to get all active LicenseCategories for the given country");

        List<LicenseCategoryDTO> licenseCategories = licenseCategoryMapper.toDto(licenseCategoryRepository.
                                            getLicenseCategoryByCountryCodeAndExchangeableAndStatusIsOrderByCode(
                                            StringUtil.upperCase(countryCode),Boolean.TRUE,
                                            LicenseCategoryStatus.ACTIVE));

        Map<String, LicenseCategoryDTO> categoriesMap = new TreeMap<>();
        for (LicenseCategoryDTO category : licenseCategories) {
            categoriesMap.put(category.getGlobalLicenseCategoryDTO().getName().getEn(), category);
        }
        return new ArrayList<>(categoriesMap.values());
    }

    /**
     * Find exchangeable active license categories  by city
     *
     * @param cityCode city code
     * @return all active license categories related to the given city
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = LicenseCategoryRepository.LicenseCategoryCache.GET_EXCHANGEABLE_LICENSE_CATEGORIES_BY_CITY_CODE_CACHE)
    public List<LicenseCategoryDTO> findExchangeableCategoriesByCity(String cityCode) {
        log.debug("Request to get all active LicenseCategories for the given city");

        List<LicenseCategoryDTO> licenseCategories = licenseCategoryMapper.toDto(licenseCategoryRepository.
            getLicenseCategoryByCityCodeAndExchangeableAndStatusIsOrderByCode(
                StringUtil.upperCase(cityCode),Boolean.TRUE,
                LicenseCategoryStatus.ACTIVE));

        Map<String, LicenseCategoryDTO> categoriesMap = new TreeMap<>();
        for (LicenseCategoryDTO category : licenseCategories) {
            categoriesMap.put(category.getGlobalLicenseCategoryDTO().getName().getEn(), category);
        }
        return new ArrayList<>(categoriesMap.values());
    }

    /**
     * Get one licenseCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LicenseCategoryDTO> findOne(Long id) {
        log.debug("Request to get LicenseCategory : {}", id);
        return licenseCategoryRepository.findById(id)
            .map(licenseCategoryMapper::toDto);
    }

    /**
     * Getter for license category entity by passed UTS mapping code
     *
     * @param utsMappingCode UTS mapping code
     * @return the entity of LicenseCategory
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LicenseCategoryDTO> findOne(Integer utsMappingCode) {
        log.debug("Request to get LicenseCategory by utsMappingCode : {}", utsMappingCode);
        return licenseCategoryRepository.getByUtsMappingCode(utsMappingCode)
            .map(licenseCategoryMapper::toDto);
    }

    /**
     * Getter for license category entity by passed code
     *
     * @param code License Category Code
     * @return the entity of LicenseCategory
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LicenseCategoryDTO> findOne(String code) {
        log.debug("Request to get LicenseCategory by code : {}", code);
        return licenseCategoryRepository.findByCode(code)
            .map(licenseCategoryMapper::toDto);
    }

    /**
     * Deactivate the licenseCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void deactivate(Long id) {
        log.debug("Request to delete LicenseCategory : {}", id);

        Optional<LicenseCategory> licenseCategory = licenseCategoryRepository.findById(id);
        if(!licenseCategory.isPresent()) {
            throw new SystemException("invalid Id passed  : {}", id);
        }
        licenseCategory.get().setStatus(LicenseCategoryStatus.INACTIVE);

        licenseCategoryRepository.save(licenseCategory.get());

        // Evict All Related Caches..
        evictAll();
    }

    public void evictAll() {
        cacheManager.getCache(LicenseCategoryRepository.LicenseCategoryCache.GET_ACTIVE_LICENSE_CATEGORIES_BY_COUNTRY_CODE).clear();
    }
}
