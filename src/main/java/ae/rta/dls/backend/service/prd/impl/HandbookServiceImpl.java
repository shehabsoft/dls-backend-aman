package ae.rta.dls.backend.service.prd.impl;

import ae.rta.dls.backend.domain.prd.Handbook;
import ae.rta.dls.backend.repository.prd.HandbookRepository;
import ae.rta.dls.backend.service.dto.prd.HandbookDTO;
import ae.rta.dls.backend.service.mapper.prd.HandbookMapper;
import ae.rta.dls.backend.service.prd.HandbookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Handbook}.
 */
@Service
@Transactional
public class HandbookServiceImpl implements HandbookService {

    private final Logger log = LoggerFactory.getLogger(HandbookServiceImpl.class);

    private final HandbookRepository handbookRepository;

    private final HandbookMapper handbookMapper;

    public HandbookServiceImpl(HandbookRepository handbookRepository, HandbookMapper handbookMapper) {
        this.handbookRepository = handbookRepository;
        this.handbookMapper = handbookMapper;
    }

    /**
     * Save a handbook.
     *
     * @param handbookDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HandbookDTO save(HandbookDTO handbookDTO) {
        log.debug("Request to save Handbook : {}", handbookDTO);
        Handbook handbook = handbookMapper.toEntity(handbookDTO);
        handbook = handbookRepository.save(handbook);
        return handbookMapper.toDto(handbook);
    }

    /**
     * Get all the handbooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HandbookDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Handbooks");
        return handbookRepository.findAll(pageable)
            .map(handbookMapper::toDto);
    }


    /**
     * Get one handbook by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HandbookDTO> findOne(Long id) {
        log.debug("Request to get Handbook : {}", id);
        return handbookRepository.findById(id)
            .map(handbookMapper::toDto);
    }

    /**
     * Find by Traffic Code No
     *
     * @param trafficCodeNo Traffic Code No
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HandbookDTO> findByTrafficCodeNo(Long trafficCodeNo) {
        log.debug("Request to get Handbook by trafficCodeNo: {}", trafficCodeNo);
        return handbookRepository.findByTrafficCodeNo(trafficCodeNo)
            .map(handbookMapper::toDto);
    }

    /**
     * Delete the handbook by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Handbook : {}", id);
        handbookRepository.deleteById(id);
    }
}
