package ae.rta.dls.backend.service.sdm.impl;

import ae.rta.dls.backend.domain.sdm.GlobalLicenseCategory;
import ae.rta.dls.backend.repository.sdm.GlobalLicenseCategoryRepository;
import ae.rta.dls.backend.service.dto.sdm.GlobalLicenseCategoryDTO;
import ae.rta.dls.backend.service.sdm.GlobalLicenseCategoryService;
import ae.rta.dls.backend.service.mapper.sdm.GlobalLicenseCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing GlobalLicenseCategory.
 */
@Service
@Transactional
public class GlobalLicenseCategoryServiceImpl implements GlobalLicenseCategoryService {

    private final Logger log = LoggerFactory.getLogger(GlobalLicenseCategoryServiceImpl.class);

    private final GlobalLicenseCategoryRepository globalLicenseCategoryRepository;

    private final GlobalLicenseCategoryMapper globalLicenseCategoryMapper;

    public GlobalLicenseCategoryServiceImpl(GlobalLicenseCategoryRepository globalLicenseCategoryRepository, GlobalLicenseCategoryMapper globalLicenseCategoryMapper) {
        this.globalLicenseCategoryRepository = globalLicenseCategoryRepository;
        this.globalLicenseCategoryMapper = globalLicenseCategoryMapper;
    }

    /**
     * Save a globalLicenseCategory.
     *
     * @param globalLicenseCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GlobalLicenseCategoryDTO save(GlobalLicenseCategoryDTO globalLicenseCategoryDTO) {
        log.debug("Request to save GlobalLicenseCategory : {}", globalLicenseCategoryDTO);

        GlobalLicenseCategory globalLicenseCategory = globalLicenseCategoryMapper.toEntity(globalLicenseCategoryDTO);
        globalLicenseCategory = globalLicenseCategoryRepository.save(globalLicenseCategory);
        return globalLicenseCategoryMapper.toDto(globalLicenseCategory);
    }

    /**
     * Get all the globalLicenseCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GlobalLicenseCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GlobalLicenseCategories");
        return globalLicenseCategoryRepository.findAll(pageable)
            .map(globalLicenseCategoryMapper::toDto);
    }


    /**
     * Get one globalLicenseCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GlobalLicenseCategoryDTO> findOne(Long id) {
        log.debug("Request to get GlobalLicenseCategory : {}", id);
        return globalLicenseCategoryRepository.findById(id)
            .map(globalLicenseCategoryMapper::toDto);
    }

    /**
     * Get the globalLicenseCategory entity.
     *
     * @param code the code of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GlobalLicenseCategoryDTO> findOne(String code) {
        log.debug("Request to get GlobalLicenseCategory : {}", code);
        return globalLicenseCategoryRepository.findByCode(code)
            .map(globalLicenseCategoryMapper::toDto);
    }

    /**
     * Delete the globalLicenseCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GlobalLicenseCategory : {}", id);
        globalLicenseCategoryRepository.deleteById(id);
    }
}
