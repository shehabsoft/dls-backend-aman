package ae.rta.dls.backend.repository.prd;

import ae.rta.dls.backend.domain.prd.MedicalFitness;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicalFitness entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalFitnessRepository extends JpaRepository<MedicalFitness, Long> {

}
