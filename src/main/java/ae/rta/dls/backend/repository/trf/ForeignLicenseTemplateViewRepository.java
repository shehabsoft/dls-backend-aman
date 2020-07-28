package ae.rta.dls.backend.repository.trf;

import ae.rta.dls.backend.domain.trf.ForeignLicenseTemplateView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the HeldLicenseTemplateView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ForeignLicenseTemplateViewRepository extends JpaRepository<ForeignLicenseTemplateView, Long> {

    Optional<ForeignLicenseTemplateView> findTop1ByCountryIdAndStateId(Long countryId, Long stateId);

    Optional<ForeignLicenseTemplateView> findTop1ByCountryCodeAndStateCode(String countryCode, String stateCode);

    Optional<ForeignLicenseTemplateView> findTop1ByCountryId(Long countryId);

    Optional<ForeignLicenseTemplateView> findTop1ByCountryCode(String countryCode);
}
