package ae.rta.dls.backend.repository.ws.rest;

import ae.rta.dls.backend.domain.ws.rest.BrmRestLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BrmRestLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrmRestLogRepository extends JpaRepository<BrmRestLog, Long> {

}
