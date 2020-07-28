package ae.rta.dls.backend.service.prd;

import ae.rta.dls.backend.service.dto.prd.LearningFileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Service Interface for managing LearningFile.
 */
public interface LearningFileService {

    /**
     * Save a learningFile.
     *
     * @param learningFileDTO the entity to save
     * @return the persisted entity
     */
    LearningFileDTO save(LearningFileDTO learningFileDTO);

    /**
     * Get all the learningFiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LearningFileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" learningFile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LearningFileDTO> findOne(Long id);

    /**
     * Get learning file by application id.
     *
     * @param applicationId the id of the entity
     * @return the entity
     */
    Optional<LearningFileDTO> findByApplicationId(Long applicationId);

    /**
     * Get active learning file by application id.
     *
     * @param applicationId the id of the entity
     * @return the entity
     */
    Optional<LearningFileDTO> findActiveLearningFileByApplicationId(Long applicationId);

    /**
     * Get active learning file by .
     *
     * @param emiratesId Emirates Id
     * @return the entity
     */
    Optional<LearningFileDTO> findActiveLearningFileByEmiratesId(String emiratesId);

    /**
     * Find active learning file by Traffic Code No
     *
     * @param trafficCodeNo Traffic Code No
     * @return the entity
     */
    Optional<LearningFileDTO> findActiveLearningFileByTrafficCodeNo(@Param("trafficCodeNo") Long trafficCodeNo);
    /**
     * Delete the "id" learningFile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
