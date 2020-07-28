package ae.rta.dls.backend.repository.sys;

import ae.rta.dls.backend.domain.enumeration.sys.JobStatus;
import ae.rta.dls.backend.domain.sys.AutomatedJobConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AutomatedJobConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutomatedJobConfigRepository extends JpaRepository<AutomatedJobConfig, Long> {

    /**
     * Cache identifiers
     */
    enum AutomatedJobConfigCache {
        AUTOMATED_JOB_BY_JOB_NAME_CACHE;
        public static final String AUTOMATED_JOB_BY_JOB = "automatedJobByJobName";
    }


    /**
     * Repository methods
     */


    /**
     * Get the automatedJobConfig by jobName
     *
     * @param jobName the name of the job
     * @return the entity
     */
    @Cacheable(cacheNames = AutomatedJobConfigRepository.AutomatedJobConfigCache.AUTOMATED_JOB_BY_JOB)
    Optional<AutomatedJobConfig> findByJobName(String jobName);

    /**
     * Getter for inactive automatedJobConfig
     *
     * @param
     * @return the entity
     */
    Page<AutomatedJobConfig> getByStatusIsNot(JobStatus status, Pageable pageable);


    /**
     * Getter for active automated job config
     *
     * @param status
     * @return List of AutomatedJobConfig
     */
    List<AutomatedJobConfig> getByStatusIs(JobStatus status);
}
