package ae.rta.dls.backend.service.prd;

import ae.rta.dls.backend.service.dto.prd.LearningFileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PrdServiceFacade {

    /**
     * Get the "id" learningFile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LearningFileDTO> findLearningFileById(Long id);

    /**
     * Get all the learningFiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LearningFileDTO> findAllLearningFile(Pageable pageable);

    /**
     * Get learning file by application id.
     *
     * @param applicationId the id of the entity
     * @return the entity
     */
    Optional<LearningFileDTO> findLearningFileByApplicationId(Long applicationId);

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
}
