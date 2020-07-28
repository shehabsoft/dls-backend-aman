package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.service.dto.sys.AutomatedJobAuditDTO;
import ae.rta.dls.backend.service.sys.AutomatedJobAuditService;
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
 * REST controller for managing AutomatedJobAudit.
 */
@RestController
@RequestMapping("/api/sys")
public class AutomatedJobAuditResource {

    private final Logger log = LoggerFactory.getLogger(AutomatedJobAuditResource.class);

    private static final String ENTITY_NAME = "automatedJobAudit";

    private final AutomatedJobAuditService automatedJobAuditService;

    public AutomatedJobAuditResource(AutomatedJobAuditService automatedJobAuditService) {
        this.automatedJobAuditService = automatedJobAuditService;
    }

    /**
     * POST  /automated-job-audits : Create a new automatedJobAudit.
     *
     * @param automatedJobAuditDTO the automatedJobAuditDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new automatedJobAuditDTO, or with status 400 (Bad Request) if the automatedJobAudit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/automated-job-audits")
    public ResponseEntity<AutomatedJobAuditDTO> createAutomatedJobAudit(@Valid @RequestBody AutomatedJobAuditDTO automatedJobAuditDTO) throws URISyntaxException {
        log.debug("REST request to save AutomatedJobAudit : {}", automatedJobAuditDTO);
        if (automatedJobAuditDTO.getId() != null) {
            throw new BadRequestAlertException("A new automatedJobAudit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutomatedJobAuditDTO result = automatedJobAuditService.save(automatedJobAuditDTO);
        return ResponseEntity.created(new URI("/api/automated-job-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /automated-job-audits : Updates an existing automatedJobAudit.
     *
     * @param automatedJobAuditDTO the automatedJobAuditDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated automatedJobAuditDTO,
     * or with status 400 (Bad Request) if the automatedJobAuditDTO is not valid,
     * or with status 500 (Internal Server Error) if the automatedJobAuditDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/automated-job-audits")
    public ResponseEntity<AutomatedJobAuditDTO> updateAutomatedJobAudit(@Valid @RequestBody AutomatedJobAuditDTO automatedJobAuditDTO) throws URISyntaxException {
        log.debug("REST request to update AutomatedJobAudit : {}", automatedJobAuditDTO);
        if (automatedJobAuditDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutomatedJobAuditDTO result = automatedJobAuditService.save(automatedJobAuditDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, automatedJobAuditDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /automated-job-audits : get all the automatedJobAudits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of automatedJobAudits in body
     */
    @GetMapping("/automated-job-audits")
    public ResponseEntity<List<AutomatedJobAuditDTO>> getAllAutomatedJobAudits(Pageable pageable) {
        log.debug("REST request to get a page of AutomatedJobAudits");
        Page<AutomatedJobAuditDTO> page = automatedJobAuditService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys/automated-job-audits");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /automated-job-audits/:id : get the "id" automatedJobAudit.
     *
     * @param id the id of the automatedJobAuditDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the automatedJobAuditDTO, or with status 404 (Not Found)
     */
    @GetMapping("/automated-job-audits/{id}")
    public ResponseEntity<AutomatedJobAuditDTO> getAutomatedJobAudit(@PathVariable Long id) {
        log.debug("REST request to get AutomatedJobAudit : {}", id);
        Optional<AutomatedJobAuditDTO> automatedJobAuditDTO = automatedJobAuditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(automatedJobAuditDTO);
    }

    /**
     * DELETE  /automated-job-audits/:id : delete the "id" automatedJobAudit.
     *
     * @param id the id of the automatedJobAuditDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/automated-job-audits/{id}")
    public ResponseEntity<Void> deleteAutomatedJobAudit(@PathVariable Long id) {
        log.debug("REST request to delete AutomatedJobAudit : {}", id);
        automatedJobAuditService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
