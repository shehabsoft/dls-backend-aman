package ae.rta.dls.backend.web.rest.sys;
import ae.rta.dls.backend.service.sys.DomainService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.sys.DomainDTO;
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
import java.util.*;

/**
 * REST controller for managing Domain.
 */
@RestController
@RequestMapping("/api")
public class DomainResource {

    private final Logger log = LoggerFactory.getLogger(DomainResource.class);

    private static final String ENTITY_NAME = "domain";

    private final DomainService domainService;

    public DomainResource(DomainService domainService) {
        this.domainService = domainService;
    }

    /**
     * POST  /domains : Create a new domain.
     *
     * @param domainDTO the domainDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new domainDTO, or with status 400 (Bad Request) if the domain has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sys/domains")
    public ResponseEntity<DomainDTO> createDomain(@Valid @RequestBody DomainDTO domainDTO) throws URISyntaxException {
        log.debug("REST request to save Domain : {}", domainDTO);
        if (domainDTO.getId() != null) {
            throw new BadRequestAlertException("A new domain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DomainDTO result = domainService.save(domainDTO);
        return ResponseEntity.created(new URI("/api/sys/domains/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /domains : Updates an existing domain.
     *
     * @param domainDTO the domainDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated domainDTO,
     * or with status 400 (Bad Request) if the domainDTO is not valid,
     * or with status 500 (Internal Server Error) if the domainDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sys/domains")
    public ResponseEntity<DomainDTO> updateDomain(@Valid @RequestBody DomainDTO domainDTO) throws URISyntaxException {
        log.debug("REST request to update Domain : {}", domainDTO);
        if (domainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id ", ENTITY_NAME, "idnull");
        }
        DomainDTO result = domainService.update(domainDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, domainDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /domains : get all the domains.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of domains in body
     */
    @GetMapping("/sys/domains")
    public ResponseEntity<List<DomainDTO>> getAllDomains(Pageable pageable) {
        log.debug("REST request to get a page of Domains");
        Page<DomainDTO> page = domainService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys/domains");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /domains/:id : get the "id" domain.
     *
     * @param id the id of the domainDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the domainDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sys/domains/{id}")
    public ResponseEntity<DomainDTO> getDomain(@PathVariable Long id) {
        log.debug("REST request to get Domain : {}", id);
        Optional<DomainDTO> domainDTO = domainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(domainDTO);
    }

    /**
     * DELETE  /domains/:id : delete the "id" domain.
     *
     * @param id the id of the domainDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sys/domains/{id}")
    public ResponseEntity<Void> deleteDomain(@PathVariable Long id) {
        log.debug("REST request to delete Domain : {}", id);
        domainService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /domains/:code : get the "code" domain.
     *
     * @param code the code of the domainDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the domainDTO, or with status 404 (Not Found)
     */
    @GetMapping("/public/sys/domains/code/{code}")
    public ResponseEntity<DomainDTO> getDomain(@PathVariable String code) {
        log.debug("REST request to get Domain by code : {}", code);
        Optional<DomainDTO> domainDTO = domainService.findOne(code);
        return ResponseUtil.wrapOrNotFound(domainDTO);
    }

    /**
     * GET  /domains/:code : get the "code" domain with excluded values.
     *
     * @param code the code of the domainDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the domainDTO, or with status 404 (Not Found)
     */
    @GetMapping("/public/sys/domains/code/{code}/{excludedDomainValues}")
    public ResponseEntity<DomainDTO> getDomainWithExcludedValues(@PathVariable String code, @PathVariable String[] excludedDomainValues) {
        log.debug("REST request to get Domain by code : {}", code);
        List<String> excludedDomainValuesArray = new ArrayList<>();
        if(excludedDomainValues != null && excludedDomainValues.length > 0) {
            excludedDomainValuesArray = Arrays.asList(excludedDomainValues);
        }

        Optional<DomainDTO> domainDTO = domainService.findOneWithExcludedValues(code,excludedDomainValuesArray);
        return ResponseUtil.wrapOrNotFound(domainDTO);
    }
}
