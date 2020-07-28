package ae.rta.dls.backend.repository.trf;

import ae.rta.dls.backend.domain.trf.DrivingLicenseView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the DrivingLicenseView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrivingLicenseViewRepository extends JpaRepository<DrivingLicenseView, Long> {

    Optional<DrivingLicenseView> findByEmiratesIdAndStatusIsNot(String emiratesId, Integer status);
}
