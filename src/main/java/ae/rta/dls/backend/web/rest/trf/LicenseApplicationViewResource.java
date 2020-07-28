package ae.rta.dls.backend.web.rest.trf;

import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.trf.LicenseApplicationView;
import ae.rta.dls.backend.service.trf.LicenseApplicationViewService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.service.dto.trf.LicenseApplicationViewDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link LicenseApplicationView}.
 */
@RestController
@RequestMapping("/api/trf")
public class LicenseApplicationViewResource {

    private final Logger log = LoggerFactory.getLogger(LicenseApplicationViewResource.class);

    private static final String ENTITY_NAME = "licenseApplicationView";

    private final LicenseApplicationViewService licenseApplicationViewService;

    public LicenseApplicationViewResource(LicenseApplicationViewService licenseApplicationViewService) {
        this.licenseApplicationViewService = licenseApplicationViewService;
    }

    /**
     * GET LicenseApplicationView by emiratesId.
     *
     * @param emiratesId the emirates id of the LicenseApplicationViewDTO to retrieve
     * the ResponseEntity with status 200 (accepted) if there is under processing license applications in etraffic, or with status
     * 404 (Not found) if the emiratesId not have under processing license application in etraffic
     */
    @GetMapping("/license-application-views/emiratesId/{emiratesId}")
    public ResponseEntity<Void> getUnderProcessingLicenseApplicationView(@PathVariable String emiratesId) {
        log.debug("REST request to get LicenseApplicationView : {}", emiratesId);
        if (StringUtil.isBlank(emiratesId)) {
            throw new BadRequestAlertException("Invalid emiratesId", ENTITY_NAME, "emiratesId is null");
        }

        Optional<LicenseApplicationViewDTO> licenseApplicationViewDTO = licenseApplicationViewService.findUnderProcessingLicenseApplicationByEmiratesId(emiratesId);

        if (!licenseApplicationViewDTO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
