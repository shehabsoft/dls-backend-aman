package ae.rta.dls.backend.web.rest.trn;
import ae.rta.dls.backend.service.trn.ApplicationPhaseService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.trn.ApplicationPhaseDTO;
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
 * REST controller for managing ApplicationPhase.
 */
@RestController
@RequestMapping("/api/trn")
public class ApplicationPhaseResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationPhaseResource.class);

    private static final String ENTITY_NAME = "dlsBackendApplicationPhase";

    private final ApplicationPhaseService applicationPhaseService;

    public ApplicationPhaseResource(ApplicationPhaseService applicationPhaseService) {
        this.applicationPhaseService = applicationPhaseService;
    }

    /**
     * POST  /application-phases : Create a new applicationPhase.
     *
     * @param applicationPhaseDTO the applicationPhaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationPhaseDTO, or with status 400 (Bad Request) if the applicationPhase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/application-phases")
    public ResponseEntity<ApplicationPhaseDTO> createApplicationPhase(@Valid @RequestBody ApplicationPhaseDTO applicationPhaseDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationPhase : {}", applicationPhaseDTO);
        if (applicationPhaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationPhase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationPhaseDTO result = applicationPhaseService.save(applicationPhaseDTO);
        return ResponseEntity.created(new URI("/api/trn/application-phases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /application-phases : Updates an existing applicationPhase.
     *
     * @param applicationPhaseDTO the applicationPhaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationPhaseDTO,
     * or with status 400 (Bad Request) if the applicationPhaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationPhaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/application-phases")
    public ResponseEntity<ApplicationPhaseDTO> updateApplicationPhase(@Valid @RequestBody ApplicationPhaseDTO applicationPhaseDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationPhase : {}", applicationPhaseDTO);
        if (applicationPhaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationPhaseDTO result = applicationPhaseService.save(applicationPhaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationPhaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /application-phases : get all the applicationPhases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of applicationPhases in body
     */
    @GetMapping("/application-phases")
    public ResponseEntity<List<ApplicationPhaseDTO>> getAllApplicationPhases(Pageable pageable) {
        log.debug("REST request to get a page of ApplicationPhases");
        Page<ApplicationPhaseDTO> page = applicationPhaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trn/application-phases");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /application-phases/:id : get the "id" applicationPhase.
     *
     * @param id the id of the applicationPhaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationPhaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/application-phases/{id}")
    public ResponseEntity<ApplicationPhaseDTO> getApplicationPhase(@PathVariable Long id) {
        log.debug("REST request to get ApplicationPhase : {}", id);
        Optional<ApplicationPhaseDTO> applicationPhaseDTO = applicationPhaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationPhaseDTO);
    }

    /**
     * DELETE  /application-phases/:id : delete the "id" applicationPhase.
     *
     * @param id the id of the applicationPhaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/application-phases/{id}")
    public ResponseEntity<Void> deleteApplicationPhase(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationPhase : {}", id);
        applicationPhaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
