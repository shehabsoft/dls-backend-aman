package ae.rta.dls.backend.repository.sys;

import ae.rta.dls.backend.domain.sys.ErrorLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ErrorLog entity.
 */
@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {

}
