package ae.rta.dls.backend.repository.trf;
import ae.rta.dls.backend.domain.trf.LicenseApplicationView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the LicenseApplicationView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LicenseApplicationViewRepository extends JpaRepository<LicenseApplicationView, Long> {

    Optional<LicenseApplicationView> findByEmiratesIdAndStatus(String emiratesId, Integer status);
}
