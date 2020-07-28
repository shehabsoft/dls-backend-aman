package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.service.dto.sys.AutomatedJobConfigDTO;
import ae.rta.dls.backend.service.sys.AutomatedJobConfigService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
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
 * REST controller for managing AutomatedJobConfig.
 */
@RestController
@RequestMapping("/api/sys")
public class AutomatedJobConfigResource {

    private final Logger log = LoggerFactory.getLogger(AutomatedJobConfigResource.class);

    private static final String ENTITY_NAME = "automatedJobConfig";

    private final AutomatedJobConfigService automatedJobConfigService;

    public AutomatedJobConfigResource(AutomatedJobConfigService automatedJobConfigService) {
        this.automatedJobConfigService = automatedJobConfigService;
    }

    /**
     * POST  /automated-job-configs : Create a new automatedJobConfig.
     *
     * @param automatedJobConfigDTO the automatedJobConfigDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new automatedJobConfigDTO, or with status 400 (Bad Request) if the automatedJobConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/automated-job-configs")
    public ResponseEntity<AutomatedJobConfigDTO> createAutomatedJobConfig(@Valid @RequestBody AutomatedJobConfigDTO automatedJobConfigDTO) throws URISyntaxException {
        log.debug("REST request to save AutomatedJobConfig : {}", automatedJobConfigDTO);
        if (automatedJobConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new automatedJobConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutomatedJobConfigDTO result = automatedJobConfigService.save(automatedJobConfigDTO);
        return ResponseEntity.created(new URI("/api/sys/automated-job-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /automated-job-configs : Updates an existing automatedJobConfig.
     *
     * @param automatedJobConfigDTO the automatedJobConfigDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated automatedJobConfigDTO,
     * or with status 400 (Bad Request) if the automatedJobConfigDTO is not valid,
     * or with status 500 (Internal Server Error) if the automatedJobConfigDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/automated-job-configs")
    public ResponseEntity<AutomatedJobConfigDTO> updateAutomatedJobConfig(@Valid @RequestBody AutomatedJobConfigDTO automatedJobConfigDTO) throws URISyntaxException {
        log.debug("REST request to update AutomatedJobConfig : {}", automatedJobConfigDTO);
        if (automatedJobConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutomatedJobConfigDTO result = automatedJobConfigService.save(automatedJobConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, automatedJobConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /automated-job-configs : get all the automatedJobConfigs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of automatedJobConfigs in body
     */
    @GetMapping("/automated-job-configs")
    public ResponseEntity<List<AutomatedJobConfigDTO>> getAllAutomatedJobConfigs(Pageable pageable) {
        log.debug("REST request to get a page of AutomatedJobConfigs");
        Page<AutomatedJobConfigDTO> page = automatedJobConfigService.getActiveJobConfigs(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys/automated-job-configs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /automated-job-configs/:id : get the "id" automatedJobConfig.
     *
     * @param id the id of the automatedJobConfigDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the automatedJobConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/automated-job-configs/{id}")
    public ResponseEntity<AutomatedJobConfigDTO> getAutomatedJobConfig(@PathVariable Long id) {
        log.debug("REST request to get AutomatedJobConfig : {}", id);
        Optional<AutomatedJobConfigDTO> automatedJobConfigDTO = automatedJobConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(automatedJobConfigDTO);
    }

    /**
     * DELETE  /automated-job-configs/:id : delete the "id" automatedJobConfig.
     *
     * @param id the id of the automatedJobConfigDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/automated-job-configs/{id}")
    public ResponseEntity<Void> deleteAutomatedJobConfig(@PathVariable Long id) {
        log.debug("REST request to delete AutomatedJobConfig : {}", id);
        automatedJobConfigService.deactivate(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
