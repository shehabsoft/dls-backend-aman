package ae.rta.dls.backend.web.rest.trf;

import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import ae.rta.dls.backend.service.dto.trf.EyeTestResultViewDTO;
import ae.rta.dls.backend.service.trf.EyeTestResultService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/trf")
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class EyeTestResultResource {

    private final Logger log = LoggerFactory.getLogger(EyeTestResultResource.class);

    private EyeTestResultService eyeTestResultService;

    public EyeTestResultResource(EyeTestResultService eyeTestResultService) {
        this.eyeTestResultService = eyeTestResultService;
    }

    /**
     * GET  /eyeTest-results/emiratesId/{emiratesId} : get eye test result by emirates ID.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of EyeTestResult in body
     */
    @GetMapping("/eyeTest-results/emiratesId/{emiratesId}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.VERIFIED_ROLE  + "\")")
    public ResponseEntity<EyeTestResultViewDTO> getByEmiratesId(@PathVariable String emiratesId) {
        log.debug("REST request to get eye test result by emirates Id {}" , emiratesId);

        Optional<EyeTestResultViewDTO> eyeTestResulResponse =  eyeTestResultService.findByEmiratesId(emiratesId);

        return ResponseUtil.wrapOrNotFound(eyeTestResulResponse);
    }
}
