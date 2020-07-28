package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import ae.rta.dls.backend.service.dto.sys.EntityAuditConfigurationDTO;
import com.codahale.metrics.annotation.Timed;
import ae.rta.dls.backend.service.sys.EntityAuditConfigurationService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EntityAuditConfiguration.
 */
@RestController
@RequestMapping("/api/sys")
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class EntityAuditConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(EntityAuditConfigurationResource.class);

    private static final String ENTITY_NAME = "entityAuditConfiguration";

    private final EntityAuditConfigurationService entityAuditConfigurationService;

    public EntityAuditConfigurationResource(EntityAuditConfigurationService entityAuditConfigurationService) {
        this.entityAuditConfigurationService = entityAuditConfigurationService;
    }

    /**
     * POST  /entity-audit-configurations : Create a new entityAuditConfiguration.
     *
     * @param entityAuditConfigurationDTO the entityAuditConfigurationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entityAuditConfiguration, or with status 400 (Bad Request) if the entityAuditConfiguration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entity-audit-configurations")
    @Timed
    public ResponseEntity<EntityAuditConfigurationDTO> createEntityAuditConfiguration(@Valid @RequestBody EntityAuditConfigurationDTO entityAuditConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save EntityAuditConfiguration : {}", entityAuditConfigurationDTO);
        if (entityAuditConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new entityAuditConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }

        EntityAuditConfigurationDTO result = entityAuditConfigurationService.save(entityAuditConfigurationDTO);
        return ResponseEntity.created(new URI("/api/sys/entity-audit-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entity-audit-configurations : Updates an existing entityAuditConfiguration.
     *
     * @param entityAuditConfigurationDTO the entityAuditConfiguration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entityAuditConfiguration,
     * or with status 400 (Bad Request) if the entityAuditConfiguration is not valid,
     * or with status 500 (Internal Server Error) if the entityAuditConfiguration couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entity-audit-configurations")
    @Timed
    public ResponseEntity<EntityAuditConfigurationDTO> updateEntityAuditConfiguration(@Valid @RequestBody EntityAuditConfigurationDTO entityAuditConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update EntityAuditConfiguration : {}", entityAuditConfigurationDTO);
        if (entityAuditConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        EntityAuditConfigurationDTO result = entityAuditConfigurationService.save(entityAuditConfigurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entity-audit-configurations : get all the entityAuditConfigurations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entityAuditConfigurations in body
     */
    @GetMapping("/entity-audit-configurations")
    @Timed
    public ResponseEntity<List<EntityAuditConfigurationDTO>> getAllEntityAuditConfigurations(Pageable pageable) {
        log.debug("REST request to get a page of EntityAuditConfigurations");
        Page<EntityAuditConfigurationDTO> page = entityAuditConfigurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys/entity-audit-configurations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /entity-audit-configurations/:id : get the "id" entityAuditConfiguration.
     *
     * @param id the id of the entityAuditConfiguration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entityAuditConfiguration, or with status 404 (Not Found)
     */
    @GetMapping("/entity-audit-configurations/{id}")
    @Timed
    public ResponseEntity<EntityAuditConfigurationDTO> getEntityAuditConfiguration(@PathVariable Long id) {
        log.debug("REST request to get EntityAuditConfiguration : {}", id);
        Optional<EntityAuditConfigurationDTO> entityAuditConfiguration = entityAuditConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entityAuditConfiguration);
    }

    /**
     * DELETE  /entity-audit-configurations/:id : delete the "id" entityAuditConfiguration.
     *
     * @param id the id of the entityAuditConfiguration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entity-audit-configurations/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntityAuditConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete EntityAuditConfiguration : {}", id);
        entityAuditConfigurationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
