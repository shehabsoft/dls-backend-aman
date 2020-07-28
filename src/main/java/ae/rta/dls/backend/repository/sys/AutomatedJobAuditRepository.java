package ae.rta.dls.backend.repository.sys;

import ae.rta.dls.backend.domain.sys.AutomatedJobAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutomatedJobAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutomatedJobAuditRepository extends JpaRepository<AutomatedJobAudit, Long> {

}
