package ae.rta.dls.backend.repository.trf;

import ae.rta.dls.backend.domain.trf.EyeTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the EyeTestResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EyeTestResultRepository extends JpaRepository<EyeTestResult, Long> {


    Optional<EyeTestResult> findByEmiratesId(String emiratesId);
}
