package ae.rta.dls.backend.web.rest.sct;

import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import ae.rta.dls.backend.service.sct.ApplicationTypeService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.sct.ApplicationTypeDTO;
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
 * REST controller for managing ApplicationType.
 */
@RestController
@RequestMapping("/api/sct")
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class ApplicationTypeResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationTypeResource.class);

    private static final String ENTITY_NAME = "applicationType";

    private final ApplicationTypeService applicationTypeService;

    public ApplicationTypeResource(ApplicationTypeService applicationTypeService) {
        this.applicationTypeService = applicationTypeService;
    }

    /**
     * POST  /application-types : Create a new applicationType.
     *
     * @param applicationTypeDTO the applicationTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationTypeDTO, or with status 400 (Bad Request) if the applicationType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/application-types")
    @Timed
    public ResponseEntity<ApplicationTypeDTO> createApplicationType(@Valid @RequestBody ApplicationTypeDTO applicationTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationType : {}", applicationTypeDTO);
        if (applicationTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationTypeDTO result = applicationTypeService.save(applicationTypeDTO);
        return ResponseEntity.created(new URI("/api/sct/application-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /application-types : Updates an existing applicationType.
     *
     * @param applicationTypeDTO the applicationTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationTypeDTO,
     * or with status 400 (Bad Request) if the applicationTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/application-types")
    @Timed
    public ResponseEntity<ApplicationTypeDTO> updateApplicationType(@Valid @RequestBody ApplicationTypeDTO applicationTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationType : {}", applicationTypeDTO);
        if (applicationTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationTypeDTO result = applicationTypeService.save(applicationTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /application-types : get all the applicationTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of applicationTypes in body
     */
    @GetMapping("/application-types")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\")")
    public ResponseEntity<List<ApplicationTypeDTO>> getActiveApplicationTypes(Pageable pageable) {

        log.debug("REST request to get a page of ApplicationTypes");
        Page<ApplicationTypeDTO> page = applicationTypeService.getActiveApplicationTypes(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sct/application-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /application-types : get all the applicationTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of applicationTypes in body
     */
    @GetMapping("/application-types/all")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\")")
    public ResponseEntity<List<ApplicationTypeDTO>> getAllApplicationTypes(Pageable pageable) {
        log.debug("REST request to get a page of ApplicationTypes");
        Page<ApplicationTypeDTO> page = applicationTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys/application-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /application-types/:id : get the "id" applicationType.
     *
     * @param id the id of the applicationTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/application-types/{id}")
    @Timed
    public ResponseEntity<ApplicationTypeDTO> getApplicationType(@PathVariable Long id) {
        log.debug("REST request to get ApplicationType : {}", id);
        Optional<ApplicationTypeDTO> applicationTypeDTO = applicationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationTypeDTO);
    }

    /**
     * GET  /application-types/code/:code : get the "code" applicationType.
     *
     * @param code the code of the applicationTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/application-types/code/{code}")
    @Timed
    public ResponseEntity<ApplicationTypeDTO> getApplicationTypeByCode(@PathVariable Long code) {
        log.debug("REST request to get ApplicationType : {}", code);
        Optional<ApplicationTypeDTO> applicationTypeDTO = applicationTypeService.getByCode(code);
        return ResponseUtil.wrapOrNotFound(applicationTypeDTO);
    }

    /**
     * DELETE  /application-types/:id : delete the "id" applicationType.
     *
     * @param id the id of the applicationTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/application-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicationType(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationType : {}", id);
        applicationTypeService.deactivate(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
