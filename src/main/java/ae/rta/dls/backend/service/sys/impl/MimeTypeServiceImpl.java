package ae.rta.dls.backend.service.sys.impl;

import ae.rta.dls.backend.service.sys.MimeTypeService;
import ae.rta.dls.backend.domain.sys.MimeType;
import ae.rta.dls.backend.repository.sys.MimeTypeRepository;
import ae.rta.dls.backend.service.dto.sys.MimeTypeDTO;
import ae.rta.dls.backend.service.mapper.sys.MimeTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MimeType.
 */
@Service
@Transactional
public class MimeTypeServiceImpl implements MimeTypeService {

    private final Logger log = LoggerFactory.getLogger(MimeTypeServiceImpl.class);

    private final CacheManager cacheManager;

    private final MimeTypeRepository mimeTypeRepository;

    private final MimeTypeMapper mimeTypeMapper;

    public MimeTypeServiceImpl(MimeTypeRepository mimeTypeRepository, MimeTypeMapper mimeTypeMapper, CacheManager cacheManager) {
        this.mimeTypeRepository = mimeTypeRepository;
        this.mimeTypeMapper = mimeTypeMapper;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a mimeType.
     *
     * @param mimeTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MimeTypeDTO save(MimeTypeDTO mimeTypeDTO) {
        log.debug("Request to save MimeType : {}", mimeTypeDTO);
        MimeType mimeType = mimeTypeMapper.toEntity(mimeTypeDTO);
        mimeType = mimeTypeRepository.save(mimeType);
        evictAll();
        return mimeTypeMapper.toDto(mimeType);
    }

    /**
     * Get all the mimeTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MimeTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MimeTypes");
        return mimeTypeRepository.findAll(pageable)
            .map(mimeTypeMapper::toDto);
    }


    /**
     * Get one mimeType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MimeTypeDTO> findOne(Long id) {
        log.debug("Request to get MimeType : {}", id);
        return mimeTypeRepository.findById(id)
            .map(mimeTypeMapper::toDto);
    }

    /**
     * Delete the mimeType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MimeType : {}", id);
        mimeTypeRepository.deleteById(id);

        evictAll();
    }

    /**
     * Get the "extension" Mime Type.
     *
     * @param extension the code of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = MimeTypeRepository.MimeTypeCache.GET_MIME_TYPE_BY_EXTENSION)
    public Optional<MimeTypeDTO> getByExtension(String extension) {
        log.debug("Request to get mime type by extension: {}", extension);
        return mimeTypeRepository.getByExtension(extension)
            .map(mimeTypeMapper::toDto);
    }

    public void evictAll() {
        cacheManager.getCache(MimeTypeRepository.MimeTypeCache.GET_MIME_TYPE_BY_EXTENSION).clear();
    }
}
