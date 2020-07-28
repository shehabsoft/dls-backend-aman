package ae.rta.dls.backend.repository.ws.rest;

import ae.rta.dls.backend.domain.ws.rest.RestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RestLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestLogRepository extends JpaRepository<RestLog, Long> {

}
