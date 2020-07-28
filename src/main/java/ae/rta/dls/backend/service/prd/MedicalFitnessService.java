package ae.rta.dls.backend.service.prd;

import ae.rta.dls.backend.service.dto.prd.MedicalFitnessDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MedicalFitness.
 */
public interface MedicalFitnessService {

    /**
     * Save a medicalFitness.
     *
     * @param medicalFitnessDTO the entity to save
     * @return the persisted entity
     */
    MedicalFitnessDTO save(MedicalFitnessDTO medicalFitnessDTO);

    /**
     * Get all the medicalFitnesses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MedicalFitnessDTO> findAll(Pageable pageable);


    /**
     * Get the "id" medicalFitness.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MedicalFitnessDTO> findOne(Long id);

    /**
     * Delete the "id" medicalFitness.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
