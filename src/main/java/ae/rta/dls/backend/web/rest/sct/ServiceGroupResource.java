package ae.rta.dls.backend.web.rest.sct;

import com.codahale.metrics.annotation.Timed;
import ae.rta.dls.backend.service.sct.ServiceGroupService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.sct.ServiceGroupDTO;
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
 * REST controller for managing ServiceGroup.
 */
@RestController
@RequestMapping("/api/sct")
public class ServiceGroupResource {

    private final Logger log = LoggerFactory.getLogger(ServiceGroupResource.class);

    private static final String ENTITY_NAME = "serviceGroup";

    private final ServiceGroupService serviceGroupService;

    public ServiceGroupResource(ServiceGroupService serviceGroupService) {
        this.serviceGroupService = serviceGroupService;
    }

    /**
     * POST  /service-groups : Create a new serviceGroup.
     *
     * @param serviceGroupDTO the serviceGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceGroupDTO, or with status 400 (Bad Request) if the serviceGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-groups")
    @Timed
    public ResponseEntity<ServiceGroupDTO> createServiceGroup(@Valid @RequestBody ServiceGroupDTO serviceGroupDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceGroup : {}", serviceGroupDTO);
        if (serviceGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceGroupDTO result = serviceGroupService.save(serviceGroupDTO);
        return ResponseEntity.created(new URI("/api/sct/service-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-groups : Updates an existing serviceGroup.
     *
     * @param serviceGroupDTO the serviceGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceGroupDTO,
     * or with status 400 (Bad Request) if the serviceGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-groups")
    @Timed
    public ResponseEntity<ServiceGroupDTO> updateServiceGroup(@Valid @RequestBody ServiceGroupDTO serviceGroupDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceGroup : {}", serviceGroupDTO);
        if (serviceGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceGroupDTO result = serviceGroupService.save(serviceGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-groups : get all the serviceGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceGroups in body
     */
    @GetMapping("/service-groups")
    @Timed
    public ResponseEntity<List<ServiceGroupDTO>> getAllServiceGroups(Pageable pageable) {
        log.debug("REST request to get a page of ServiceGroups");
        Page<ServiceGroupDTO> page = serviceGroupService.findActiveServiceGroup(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sct/service-groups");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /service-groups/:id : get the "id" serviceGroup.
     *
     * @param id the id of the serviceGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-groups/{id}")
    @Timed
    public ResponseEntity<ServiceGroupDTO> getServiceGroup(@PathVariable Long id) {
        log.debug("REST request to get ServiceGroup : {}", id);
        Optional<ServiceGroupDTO> serviceGroupDTO = serviceGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceGroupDTO);
    }

    /**
     * DELETE  /service-groups/:id : delete the "id" serviceGroup.
     *
     * @param id the id of the serviceGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceGroup(@PathVariable Long id) {
        log.debug("REST request to delete ServiceGroup : {}", id);
        serviceGroupService.deactivate(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
