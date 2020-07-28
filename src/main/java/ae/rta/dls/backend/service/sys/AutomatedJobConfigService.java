package ae.rta.dls.backend.service.sys;

import ae.rta.dls.backend.service.dto.sys.AutomatedJobConfigDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AutomatedJobConfig.
 */
public interface AutomatedJobConfigService {

    /**
     * Save a automatedJobConfig.
     *
     * @param automatedJobConfigDTO the entity to save
     * @return the persisted entity
     */
    AutomatedJobConfigDTO save(AutomatedJobConfigDTO automatedJobConfigDTO);

    /**
     * Get all the automatedJobConfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AutomatedJobConfigDTO> findAll(Pageable pageable);

    /**
     * Get Active Automated Job Configs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AutomatedJobConfigDTO> getActiveJobConfigs(Pageable pageable);

    /**
     * Get the "id" automatedJobConfig.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AutomatedJobConfigDTO> findOne(Long id);

    /**
     * Deactivate the "id" automatedJobConfig.
     *
     * @param id the id of the entity
     */
    void deactivate(Long id);

    /**
     * Get the automatedJobConfig by jobName
     *
     * @param jobName the name of the job
     * @return the entity
     */
    Optional<AutomatedJobConfigDTO> findByJobName(String jobName);


    /**
     * Get Active Automated Job Config List.
     *
     * @return the list of entities
     */
    List<AutomatedJobConfigDTO> getActiveJobConfigList();
}
