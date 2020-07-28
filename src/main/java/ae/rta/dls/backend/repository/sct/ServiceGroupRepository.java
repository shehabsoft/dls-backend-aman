package ae.rta.dls.backend.repository.sct;

import ae.rta.dls.backend.domain.enumeration.trn.ServiceGroupStatus;
import ae.rta.dls.backend.domain.sct.ServiceGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ServiceGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceGroupRepository extends JpaRepository<ServiceGroup, Long> {

    @EntityGraph(attributePaths = "services")
    Optional<ServiceGroup> findById(Long id);

    @EntityGraph(attributePaths = "services")
    Page<ServiceGroup> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "services")
    Page<ServiceGroup> findByStatusIsNot(ServiceGroupStatus status, Pageable pageable);
}
