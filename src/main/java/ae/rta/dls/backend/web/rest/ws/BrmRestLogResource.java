package ae.rta.dls.backend.web.rest.ws;

import ae.rta.dls.backend.service.dto.ws.rest.BrmRestLogDTO;
import ae.rta.dls.backend.service.ws.rest.BrmRestLogService;
import com.codahale.metrics.annotation.Timed;
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
 * REST controller for managing BrmRestLog.
 */
@RestController
@RequestMapping("/api/ws")
public class BrmRestLogResource {

    private final Logger log = LoggerFactory.getLogger(BrmRestLogResource.class);

    private static final String ENTITY_NAME = "dlsBackendBrmRestLog";

    private final BrmRestLogService brmRestLogService;

    public BrmRestLogResource(BrmRestLogService brmRestLogService) {
        this.brmRestLogService = brmRestLogService;
    }

    /**
     * POST  /brm-rest-logs : Create a new brmRestLog.
     *
     * @param brmRestLogDTO the brmRestLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new brmRestLogDTO, or with status 400 (Bad Request) if the brmRestLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/brm-rest-logs")
    @Timed
    public ResponseEntity<BrmRestLogDTO> createBrmRestLog(@Valid @RequestBody BrmRestLogDTO brmRestLogDTO) throws URISyntaxException {
        log.debug("REST request to save BrmRestLog : {}", brmRestLogDTO);
        if (brmRestLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new brmRestLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BrmRestLogDTO result = brmRestLogService.save(brmRestLogDTO);
        return ResponseEntity.created(new URI("/api/ws/brm-rest-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brm-rest-logs : Updates an existing brmRestLog.
     *
     * @param brmRestLogDTO the brmRestLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated brmRestLogDTO,
     * or with status 400 (Bad Request) if the brmRestLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the brmRestLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/brm-rest-logs")
    @Timed
    public ResponseEntity<BrmRestLogDTO> updateBrmRestLog(@Valid @RequestBody BrmRestLogDTO brmRestLogDTO) throws URISyntaxException {
        log.debug("REST request to update BrmRestLog : {}", brmRestLogDTO);
        if (brmRestLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BrmRestLogDTO result = brmRestLogService.save(brmRestLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, brmRestLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brm-rest-logs : get all the brmRestLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of brmRestLogs in body
     */
    @GetMapping("/brm-rest-logs")
    @Timed
    public ResponseEntity<List<BrmRestLogDTO>> getAllBrmRestLogs(Pageable pageable) {
        log.debug("REST request to get a page of BrmRestLogs");
        Page<BrmRestLogDTO> page = brmRestLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ws/brm-rest-logs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /brm-rest-logs/:id : get the "id" brmRestLog.
     *
     * @param id the id of the brmRestLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the brmRestLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/brm-rest-logs/{id}")
    @Timed
    public ResponseEntity<BrmRestLogDTO> getBrmRestLog(@PathVariable Long id) {
        log.debug("REST request to get BrmRestLog : {}", id);
        Optional<BrmRestLogDTO> brmRestLogDTO = brmRestLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(brmRestLogDTO);
    }

    /**
     * DELETE  /brm-rest-logs/:id : delete the "id" brmRestLog.
     *
     * @param id the id of the brmRestLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/brm-rest-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteBrmRestLog(@PathVariable Long id) {
        log.debug("REST request to delete BrmRestLog : {}", id);
        brmRestLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
