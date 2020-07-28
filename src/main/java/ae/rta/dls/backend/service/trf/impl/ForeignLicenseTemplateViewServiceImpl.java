package ae.rta.dls.backend.service.trf.impl;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.service.trf.ForeignLicenseTemplateViewService;
import ae.rta.dls.backend.repository.trf.ForeignLicenseTemplateViewRepository;
import ae.rta.dls.backend.service.dto.trf.ForeignLicenseTemplateViewDTO;
import ae.rta.dls.backend.service.mapper.trf.ForeignLicenseTemplateViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Service Implementation for managing ForeignLicenseTemplateView.
 */
@Service
@Transactional
public class ForeignLicenseTemplateViewServiceImpl implements ForeignLicenseTemplateViewService {

    private final Logger log = LoggerFactory.getLogger(ForeignLicenseTemplateViewServiceImpl.class);

    private final ForeignLicenseTemplateViewRepository foreignLicenseTemplateViewRepository;

    private final ForeignLicenseTemplateViewMapper foreignLicenseTemplateViewMapper;

    public ForeignLicenseTemplateViewServiceImpl(ForeignLicenseTemplateViewRepository foreignLicenseTemplateViewRepository, ForeignLicenseTemplateViewMapper foreignLicenseTemplateViewMapper) {
        this.foreignLicenseTemplateViewRepository = foreignLicenseTemplateViewRepository;
        this.foreignLicenseTemplateViewMapper = foreignLicenseTemplateViewMapper;
    }

    /**
     * Get Foreign License template by id
     *
     * @param id: Template Id
     *
     * @return ForeignLicenseTemplateViewDTO entity
     */
    public Optional<ForeignLicenseTemplateViewDTO> findOne(Long id) {
        log.debug("request to find one by id : {}", id);
        return foreignLicenseTemplateViewRepository.findById(id)
            .map(foreignLicenseTemplateViewMapper::toDto);
    }

    /**
     * Get Foreign License template by CountryId And StateId.
     *
     * @param countryId: Country Id.
     * @param stateId: State Id.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ForeignLicenseTemplateViewDTO> findOneByCountryIdAndStateId(Long countryId, Long stateId) {
        return foreignLicenseTemplateViewRepository.findTop1ByCountryIdAndStateId(countryId, stateId)
            .map(foreignLicenseTemplateViewMapper::toDto);
    }

    /**
     * Get Foreign License template by CountryCode And StateCode.
     *
     * @param countryCode: Country Code.
     * @param stateCode: State Code.
     */
    public Optional<ForeignLicenseTemplateViewDTO> findOneByCountryCodeAndStateCode(String countryCode, String stateCode) {
        return foreignLicenseTemplateViewRepository.findTop1ByCountryCodeAndStateCode(countryCode, stateCode)
            .map(foreignLicenseTemplateViewMapper::toDto);
    }

    /**
     * Get Foreign License template by CountryId.
     *
     * @param countryId: Country Id.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ForeignLicenseTemplateViewDTO> findOneByCountryId(Long countryId) {
        try {
            return foreignLicenseTemplateViewRepository.findTop1ByCountryId(countryId)
                .map(foreignLicenseTemplateViewMapper::toDto);
        } catch(IncorrectResultSizeDataAccessException ex) {
            throw new SystemException("more than one record retrieved from HeldLicenseTemplateView");
        }
    }

    /**
     * Get Foreign License template by CountryCode.
     *
     * @param countryCode: Country Code.
     */
    public Optional<ForeignLicenseTemplateViewDTO> findOneByCountryCode(String countryCode) {
        try {
            return foreignLicenseTemplateViewRepository.findTop1ByCountryCode(countryCode)
                .map(foreignLicenseTemplateViewMapper::toDto);
        } catch(IncorrectResultSizeDataAccessException ex) {
            throw new SystemException("more than one record retrieved from HeldLicenseTemplateView");
        }
    }
}
