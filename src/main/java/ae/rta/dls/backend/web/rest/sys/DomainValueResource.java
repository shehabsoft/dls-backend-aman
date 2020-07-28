package ae.rta.dls.backend.web.rest.sys;
import ae.rta.dls.backend.service.sys.DomainValueService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.sys.DomainValueDTO;
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
 * REST controller for managing DomainValue.
 */
@RestController
@RequestMapping("/api/sys")
public class DomainValueResource {

    private final Logger log = LoggerFactory.getLogger(DomainValueResource.class);

    private static final String ENTITY_NAME = "domainValue";

    private final DomainValueService domainValueService;

    public DomainValueResource(DomainValueService domainValueService) {
        this.domainValueService = domainValueService;
    }

    /**
     * POST  /domain-values : Create a new domainValue.
     *
     * @param domainValueDTO the domainValueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new domainValueDTO, or with status 400 (Bad Request) if the domainValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/domain-values")
    public ResponseEntity<DomainValueDTO> createDomainValue(@Valid @RequestBody DomainValueDTO domainValueDTO) throws URISyntaxException {
        log.debug("REST request to save DomainValue : {}", domainValueDTO);
        if (domainValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new domainValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DomainValueDTO result = domainValueService.save(domainValueDTO);
        return ResponseEntity.created(new URI("/api/sys/domain-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /domain-values : Updates an existing domainValue.
     *
     * @param domainValueDTO the domainValueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated domainValueDTO,
     * or with status 400 (Bad Request) if the domainValueDTO is not valid,
     * or with status 500 (Internal Server Error) if the domainValueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/domain-values")
    public ResponseEntity<DomainValueDTO> updateDomainValue(@Valid @RequestBody DomainValueDTO domainValueDTO) throws URISyntaxException {
        log.debug("REST request to update DomainValue : {}", domainValueDTO);
        if (domainValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DomainValueDTO result = domainValueService.save(domainValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, domainValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /domain-values : get all the domainValues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of domainValues in body
     */
    @GetMapping("/domain-values")
    public ResponseEntity<List<DomainValueDTO>> getAllDomainValues(Pageable pageable) {
        log.debug("REST request to get a page of DomainValues");
        Page<DomainValueDTO> page = domainValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys/domain-values");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /domain-values/:id : get the "id" domainValue.
     *
     * @param id the id of the domainValueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the domainValueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/domain-values/{id}")
    public ResponseEntity<DomainValueDTO> getDomainValue(@PathVariable Long id) {
        log.debug("REST request to get DomainValue : {}", id);
        Optional<DomainValueDTO> domainValueDTO = domainValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(domainValueDTO);
    }

    /**
     * DELETE  /domain-values/:id : delete the "id" domainValue.
     *
     * @param id the id of the domainValueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/domain-values/{id}")
    public ResponseEntity<Void> deleteDomainValue(@PathVariable Long id) {
        log.debug("REST request to delete DomainValue : {}", id);
        domainValueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
