package ae.rta.dls.backend.web.rest.trf;

import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import ae.rta.dls.backend.service.trf.ForeignLicenseTemplateViewService;
import ae.rta.dls.backend.service.dto.trf.ForeignLicenseTemplateViewDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * REST controller for managing Foreign License Template View.
 */
@RestController
@RequestMapping("/api/trf/foreign-license-template-views")
public class ForeignLicenseTemplateViewResource {

    private final Logger log = LoggerFactory.getLogger(ForeignLicenseTemplateViewResource.class);

    private final ForeignLicenseTemplateViewService foreignLicenseTemplateViewService;

    public ForeignLicenseTemplateViewResource(ForeignLicenseTemplateViewService foreignLicenseTemplateViewService) {
        this.foreignLicenseTemplateViewService = foreignLicenseTemplateViewService;
    }

    /**
     * GET  /held-license-template-views/:id : get the "id" foreignLicenseTemplateView.
     *
     * @param id the id of the heldLicenseTemplateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foreignLicenseTemplateViewDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE  + "\")")
    public ResponseEntity<ForeignLicenseTemplateViewDTO> getForeignLicenseTemplateView(@PathVariable Long id) {
        log.debug("REST request to get HeldLicenseTemplate by id : {}", id);
        Optional<ForeignLicenseTemplateViewDTO> heldLicenseTemplateViewDTO = foreignLicenseTemplateViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(heldLicenseTemplateViewDTO);
    }

    /**
     * GET  /held-license-template-views/:id : get the "id" foreignLicenseTemplateView.
     *
     * @param countryId the id of the heldLicenseTemplateViewDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the heldLicenseTemplateViewDTO, or with status 404 (Not Found)
     */
    @GetMapping("/countryId/{countryId}")
    public ResponseEntity<ForeignLicenseTemplateViewDTO> getForeignLicenseTemplateViewByCountryId(@PathVariable Long countryId) {
        log.debug("REST request to get HeldLicenseTemplateView : {}", countryId);
        Optional<ForeignLicenseTemplateViewDTO> heldLicenseTemplateViewDTO = foreignLicenseTemplateViewService.findOneByCountryId(countryId);
        return ResponseUtil.wrapOrNotFound(heldLicenseTemplateViewDTO);
    }

    /**
     * GET  /held-license-template-views/:id : get the "id" foreignLicenseTemplateView.
     *
     * @param countryId the country id of the heldLicenseTemplateViewDTO to retrieve
     * @param stateId the country id of the heldLicenseTemplateViewDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the heldLicenseTemplateViewDTO, or with status 404 (Not Found)
     */
    @GetMapping("/countryId/{countryId}/stateId/{stateId}")
    public ResponseEntity<ForeignLicenseTemplateViewDTO> getForeignLicenseTemplateViewByCountryIdAndStateId(@PathVariable Long countryId, @PathVariable Long stateId) {
        log.debug("REST request to get HeldLicenseTemplateView : {} , {}", countryId , stateId);
        Optional<ForeignLicenseTemplateViewDTO> heldLicenseTemplateViewDTO = foreignLicenseTemplateViewService.findOneByCountryIdAndStateId(countryId, stateId);
        return ResponseUtil.wrapOrNotFound(heldLicenseTemplateViewDTO);
    }
}
