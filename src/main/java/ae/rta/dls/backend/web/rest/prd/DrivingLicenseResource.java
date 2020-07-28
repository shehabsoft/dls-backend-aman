package ae.rta.dls.backend.web.rest.prd;
import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import ae.rta.dls.backend.service.prd.DrivingLicenseService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.prd.DrivingLicenseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DrivingLicense.
 */
@RestController
@RequestMapping("/api/prd")
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class DrivingLicenseResource {

    private final Logger log = LoggerFactory.getLogger(DrivingLicenseResource.class);

    private static final String ENTITY_NAME = "dlsBackendDrivingLicense";

    private final DrivingLicenseService drivingLicenseService;

    public DrivingLicenseResource(DrivingLicenseService drivingLicenseService) {
        this.drivingLicenseService = drivingLicenseService;
    }

    /**
     * POST  /driving-licenses : Create a new drivingLicense.
     *
     * @param drivingLicenseDTO the drivingLicenseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drivingLicenseDTO, or with status 400 (Bad Request) if the drivingLicense has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/driving-licenses")
    public ResponseEntity<DrivingLicenseDTO> createDrivingLicense(@Valid @RequestBody DrivingLicenseDTO drivingLicenseDTO) throws URISyntaxException {
        log.debug("REST request to save DrivingLicense : {}", drivingLicenseDTO);
        if (drivingLicenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new drivingLicense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrivingLicenseDTO result = drivingLicenseService.save(drivingLicenseDTO);
        return ResponseEntity.created(new URI("/api/prd/driving-licenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /driving-licenses : Updates an existing drivingLicense.
     *
     * @param drivingLicenseDTO the drivingLicenseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drivingLicenseDTO,
     * or with status 400 (Bad Request) if the drivingLicenseDTO is not valid,
     * or with status 500 (Internal Server Error) if the drivingLicenseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/driving-licenses")
    public ResponseEntity<DrivingLicenseDTO> updateDrivingLicense(@Valid @RequestBody DrivingLicenseDTO drivingLicenseDTO) throws URISyntaxException {
        log.debug("REST request to update DrivingLicense : {}", drivingLicenseDTO);
        if (drivingLicenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DrivingLicenseDTO result = drivingLicenseService.save(drivingLicenseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, drivingLicenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /driving-licenses : get all the drivingLicenses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of drivingLicenses in body
     */
    @GetMapping("/driving-licenses")
    public ResponseEntity<List<DrivingLicenseDTO>> getAllDrivingLicenses(Pageable pageable) {
        log.debug("REST request to get a page of DrivingLicenses");
        Page<DrivingLicenseDTO> page = drivingLicenseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prd/driving-licenses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /driving-licenses/:id : get the "id" drivingLicense.
     *
     * @param id the id of the drivingLicenseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drivingLicenseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/driving-licenses/{id}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.VERIFIED_ROLE  + "\")")
    public ResponseEntity<DrivingLicenseDTO> getDrivingLicense(@PathVariable Long id) {
        log.debug("REST request to get DrivingLicense : {}", id);
        Optional<DrivingLicenseDTO> drivingLicenseDTO = drivingLicenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drivingLicenseDTO);
    }

    /**
     * GET  /driving-licenses/:id : get the "id" drivingLicense.
     *
     * @param licenseNo the license no of the drivingLicenseDTO to retrieve
     * @param licenseIssueDate License issue date
     * @return the ResponseEntity with status 200 (OK) and with body the drivingLicenseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/driving-licenses/licenseNo/{licenseNo}/licenseIssueDate/{licenseIssueDate}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE  + "\")")
    public ResponseEntity<DrivingLicenseDTO> getDrivingLicense(@PathVariable (value = "licenseNo") String licenseNo ,@PathVariable(value = "licenseIssueDate") String licenseIssueDate) {
        log.debug("REST request to get DrivingLicense : {} {} ", licenseNo , licenseIssueDate);
        Optional<DrivingLicenseDTO> drivingLicenseDTO = drivingLicenseService.findOneByLicenseNoAndIssueDate(licenseNo,licenseIssueDate);
        return ResponseUtil.wrapOrNotFound(drivingLicenseDTO);
	}
     /**
     * GET  /driving-licenses/trafficCodeNo/:trafficCodeNo
     *
     * @param trafficCodeNo customer traffic code no
     * @return the ResponseEntity with status 200 (OK) and with body the drivingLicenseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/driving-licenses/trafficCodeNo/{trafficCodeNo}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.VERIFIED_ROLE  + "\")")
    public ResponseEntity<DrivingLicenseDTO> getByTrafficFileNo(@PathVariable String trafficCodeNo) {
        log.debug("REST request to get DrivingLicense by traffic code no: {}", trafficCodeNo);
        Optional<DrivingLicenseDTO> drivingLicenseDTO = drivingLicenseService.findOne(trafficCodeNo);
        return ResponseUtil.wrapOrNotFound(drivingLicenseDTO);
    }

    /**
     * DELETE  /driving-licenses/:id : delete the "id" drivingLicense.
     *
     * @param id the id of the drivingLicenseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/driving-licenses/{id}")
    public ResponseEntity<Void> deleteDrivingLicense(@PathVariable Long id) {
        log.debug("REST request to delete DrivingLicense : {}", id);
        drivingLicenseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
