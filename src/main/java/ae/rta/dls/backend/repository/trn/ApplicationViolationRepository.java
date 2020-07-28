package ae.rta.dls.backend.repository.trn;

import ae.rta.dls.backend.domain.trn.ApplicationViolation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplicationViolation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationViolationRepository extends JpaRepository<ApplicationViolation, Long> {

}
