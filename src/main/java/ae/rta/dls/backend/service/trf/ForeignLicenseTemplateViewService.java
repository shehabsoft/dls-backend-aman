package ae.rta.dls.backend.service.trf;

import ae.rta.dls.backend.service.dto.trf.ForeignLicenseTemplateViewDTO;
import java.util.Optional;

/**
 * Service Interface for managing ForeignLicenseTemplateView.
 */
public interface ForeignLicenseTemplateViewService {

    /**
     * Get Foreign License template by id
     *
     * @param id: Template Id
     *
     * @return HeldLicenseTemplateViewDTO entity
     */
    Optional<ForeignLicenseTemplateViewDTO> findOne(Long id);

    /**
     * Get Foreign License template by CountryId And StateId.
     *
     * @param countryId: Country Id.
     * @param stateId: State Id.
     */
    Optional<ForeignLicenseTemplateViewDTO> findOneByCountryIdAndStateId(Long countryId, Long stateId);

    /**
     * Get Foreign License template by CountryCode And StateCode.
     *
     * @param countryCode: Country Code.
     * @param stateCode: State Code.
     */
    Optional<ForeignLicenseTemplateViewDTO> findOneByCountryCodeAndStateCode(String countryCode, String stateCode);

    /**
     * Get Foreign License template by CountryId.
     *
     * @param countryId: Country Id.
     */
    Optional<ForeignLicenseTemplateViewDTO> findOneByCountryId(Long countryId);

    /**
     * Get Foreign License template by CountryCode.
     *
     * @param countryCode: Country Code.
     */
    Optional<ForeignLicenseTemplateViewDTO> findOneByCountryCode(String countryCode);
}
