package ae.rta.dls.backend.repository.trn;

import ae.rta.dls.backend.domain.trn.ApplicationPhase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplicationPhase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationPhaseRepository extends JpaRepository<ApplicationPhase, Long> {

    /**
     * Get First Record of Application Phase By Application Id Order By PhaseSequence Descending
     * @param applicationId : Application Id
     *
     * @return Application Phase Entity
     */
    ApplicationPhase getFirstByApplication_IdOrderByPhaseSequenceDesc(Long applicationId);


}
