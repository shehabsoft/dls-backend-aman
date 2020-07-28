package ae.rta.dls.backend.web.rest.trf;
import ae.rta.dls.backend.service.trf.DrivingLicenseViewService;
import ae.rta.dls.backend.service.dto.trf.DrivingLicenseViewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing DrivingLicenseView.
 */
@RestController
@RequestMapping("/api/trf")
public class DrivingLicenseViewResource {

    private final Logger log = LoggerFactory.getLogger(DrivingLicenseViewResource.class);

    private final DrivingLicenseViewService drivingLicenseViewService;

    public DrivingLicenseViewResource(DrivingLicenseViewService drivingLicenseViewService) {
        this.drivingLicenseViewService = drivingLicenseViewService;
    }

    /**
     * GET DrivingLicenseView by emiratesId.
     *
     * @param emiratesId the emirates id of the drivingLicenseViewDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drivingLicenseViewDTO, or with status 404 (Not Found)
     */
    @GetMapping("/driving-license-views/emiratesId/{emiratesId}")
    public ResponseEntity<Void> getDrivingLicenseView(@PathVariable String emiratesId) {
        log.debug("REST request to get DrivingLicenseView : {}", emiratesId);
        Optional<DrivingLicenseViewDTO> drivingLicenseViewDTO = drivingLicenseViewService.findActiveByEmiratesId(emiratesId);

        if (!drivingLicenseViewDTO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
