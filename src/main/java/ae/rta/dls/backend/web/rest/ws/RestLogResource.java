package ae.rta.dls.backend.web.rest.ws;

import ae.rta.dls.backend.service.dto.ws.rest.RestLogDTO;
import ae.rta.dls.backend.service.ws.rest.RestLogService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
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
 * REST controller for managing RestLog.
 */
@RestController
@RequestMapping("/api/ws")
public class RestLogResource {

    private final Logger log = LoggerFactory.getLogger(RestLogResource.class);

    private static final String ENTITY_NAME = "restLog";

    private final RestLogService restLogService;

    public RestLogResource(RestLogService restLogService) {
        this.restLogService = restLogService;
    }

    /**
     * POST  /rest-logs : Create a new restLog.
     *
     * @param restLogDTO the restLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new restLogDTO, or with status 400 (Bad Request) if the restLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rest-logs")
    @Timed
    public ResponseEntity<RestLogDTO> createRestLog(@Valid @RequestBody RestLogDTO restLogDTO) throws URISyntaxException {
        log.debug("REST request to save RestLog : {}", restLogDTO);
        if (restLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new restLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RestLogDTO result = restLogService.save(restLogDTO);
        return ResponseEntity.created(new URI("/api/ws/rest-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rest-logs : Updates an existing restLog.
     *
     * @param restLogDTO the restLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated restLogDTO,
     * or with status 400 (Bad Request) if the restLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the restLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rest-logs")
    @Timed
    public ResponseEntity<RestLogDTO> updateRestLog(@Valid @RequestBody RestLogDTO restLogDTO) throws URISyntaxException {
        log.debug("REST request to update RestLog : {}", restLogDTO);
        if (restLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RestLogDTO result = restLogService.save(restLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, restLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rest-logs : get all the restLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of restLogs in body
     */
    @GetMapping("/rest-logs")
    @Timed
    public ResponseEntity<List<RestLogDTO>> getAllRestLogs(Pageable pageable) {
        log.debug("REST request to get a page of RestLogs");
        Page<RestLogDTO> page = restLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ws/rest-logs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rest-logs/:id : get the "id" restLog.
     *
     * @param id the id of the restLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the restLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rest-logs/{id}")
    @Timed
    public ResponseEntity<RestLogDTO> getRestLog(@PathVariable Long id) {
        log.debug("REST request to get RestLog : {}", id);
        Optional<RestLogDTO> restLogDTO = restLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restLogDTO);
    }

    /**
     * DELETE  /rest-logs/:id : delete the "id" restLog.
     *
     * @param id the id of the restLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rest-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRestLog(@PathVariable Long id) {
        log.debug("REST request to delete RestLog : {}", id);
        restLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
