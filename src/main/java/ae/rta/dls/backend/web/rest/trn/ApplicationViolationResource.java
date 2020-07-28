package ae.rta.dls.backend.web.rest.trn;
import ae.rta.dls.backend.service.trn.ApplicationViolationService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.trn.ApplicationViolationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ApplicationViolation.
 */
@RestController
@RequestMapping("/api/trn")
public class ApplicationViolationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationViolationResource.class);

    private static final String ENTITY_NAME = "dlsBackendApplicationViolation";

    private final ApplicationViolationService applicationViolationService;

    public ApplicationViolationResource(ApplicationViolationService applicationViolationService) {
        this.applicationViolationService = applicationViolationService;
    }

    /**
     * POST  /application-violations : Create a new applicationViolation.
     *
     * @param applicationViolationDTO the applicationViolationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationViolationDTO, or with status 400 (Bad Request) if the applicationViolation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/application-violations")
    public ResponseEntity<ApplicationViolationDTO> createApplicationViolation(@Valid @RequestBody ApplicationViolationDTO applicationViolationDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationViolation : {}", applicationViolationDTO);
        if (applicationViolationDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationViolation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationViolationDTO result = applicationViolationService.save(applicationViolationDTO);
        return ResponseEntity.created(new URI("/api/trn/application-violations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /application-violations : Updates an existing applicationViolation.
     *
     * @param applicationViolationDTO the applicationViolationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationViolationDTO,
     * or with status 400 (Bad Request) if the applicationViolationDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationViolationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/application-violations")
    public ResponseEntity<ApplicationViolationDTO> updateApplicationViolation(@Valid @RequestBody ApplicationViolationDTO applicationViolationDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationViolation : {}", applicationViolationDTO);
        if (applicationViolationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationViolationDTO result = applicationViolationService.save(applicationViolationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationViolationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /application-violations : get all the applicationViolations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of applicationViolations in body
     */
    @GetMapping("/application-violations")
    public ResponseEntity<List<ApplicationViolationDTO>> getAllApplicationViolations(Pageable pageable) {
        log.debug("REST request to get a page of ApplicationViolations");
        Page<ApplicationViolationDTO> page = applicationViolationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trn/application-violations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /application-violations/:id : get the "id" applicationViolation.
     *
     * @param id the id of the applicationViolationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationViolationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/application-violations/{id}")
    public ResponseEntity<ApplicationViolationDTO> getApplicationViolation(@PathVariable Long id) {
        log.debug("REST request to get ApplicationViolation : {}", id);
        Optional<ApplicationViolationDTO> applicationViolationDTO = applicationViolationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationViolationDTO);
    }

    /**
     * DELETE  /application-violations/:id : delete the "id" applicationViolation.
     *
     * @param id the id of the applicationViolationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/application-violations/{id}")
    public ResponseEntity<Void> deleteApplicationViolation(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationViolation : {}", id);
        applicationViolationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
