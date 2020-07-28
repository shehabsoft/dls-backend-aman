package ae.rta.dls.backend.service.prd.impl;

import ae.rta.dls.backend.service.prd.MedicalFitnessService;
import ae.rta.dls.backend.domain.prd.MedicalFitness;
import ae.rta.dls.backend.repository.prd.MedicalFitnessRepository;
import ae.rta.dls.backend.service.dto.prd.MedicalFitnessDTO;
import ae.rta.dls.backend.service.mapper.prd.MedicalFitnessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MedicalFitness.
 */
@Service
@Transactional
public class MedicalFitnessServiceImpl implements MedicalFitnessService {

    private final Logger log = LoggerFactory.getLogger(MedicalFitnessServiceImpl.class);

    private final MedicalFitnessRepository medicalFitnessRepository;

    private final MedicalFitnessMapper medicalFitnessMapper;

    public MedicalFitnessServiceImpl(MedicalFitnessRepository medicalFitnessRepository, MedicalFitnessMapper medicalFitnessMapper) {
        this.medicalFitnessRepository = medicalFitnessRepository;
        this.medicalFitnessMapper = medicalFitnessMapper;
    }

    /**
     * Save a medicalFitness.
     *
     * @param medicalFitnessDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MedicalFitnessDTO save(MedicalFitnessDTO medicalFitnessDTO) {
        log.debug("Request to save MedicalFitness : {}", medicalFitnessDTO);
        MedicalFitness medicalFitness = medicalFitnessMapper.toEntity(medicalFitnessDTO);
        medicalFitness = medicalFitnessRepository.save(medicalFitness);
        return medicalFitnessMapper.toDto(medicalFitness);
    }

    /**
     * Get all the medicalFitnesses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedicalFitnessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalFitnesses");
        return medicalFitnessRepository.findAll(pageable)
            .map(medicalFitnessMapper::toDto);
    }


    /**
     * Get one medicalFitness by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalFitnessDTO> findOne(Long id) {
        log.debug("Request to get MedicalFitness : {}", id);
        return medicalFitnessRepository.findById(id)
            .map(medicalFitnessMapper::toDto);
    }

    /**
     * Delete the medicalFitness by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalFitness : {}", id);
        medicalFitnessRepository.deleteById(id);
    }
}
