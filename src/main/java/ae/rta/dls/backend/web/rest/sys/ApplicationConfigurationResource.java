package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.service.sys.ApplicationConfigurationService;
import com.codahale.metrics.annotation.Timed;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.sys.ApplicationConfigurationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ApplicationConfiguration.
 */
@RestController
@RequestMapping("/api/sys")
public class ApplicationConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationConfigurationResource.class);

    private static final String ENTITY_NAME = "applicationConfiguration";

    private final ApplicationConfigurationService applicationConfigurationService;

    public ApplicationConfigurationResource(ApplicationConfigurationService applicationConfigurationService) {
        this.applicationConfigurationService = applicationConfigurationService;
    }

    /**
     * POST  /application-configurations : Create a new applicationConfiguration.
     *
     * @param applicationConfigurationDTO the applicationConfigurationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationConfigurationDTO, or with status 400 (Bad Request) if the applicationConfiguration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/application-configurations")
    @Timed
    public ResponseEntity<ApplicationConfigurationDTO> createApplicationConfiguration(@Valid @RequestBody ApplicationConfigurationDTO applicationConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationConfiguration : {}", applicationConfigurationDTO);
        if (applicationConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationConfigurationDTO result = applicationConfigurationService.save(applicationConfigurationDTO);
        return ResponseEntity.created(new URI("/api/sys/application-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /application-configurations : Updates an existing applicationConfiguration.
     *
     * @param applicationConfigurationDTO the applicationConfigurationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationConfigurationDTO,
     * or with status 400 (Bad Request) if the applicationConfigurationDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationConfigurationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/application-configurations")
    @Timed
    public ResponseEntity<ApplicationConfigurationDTO> updateApplicationConfiguration(@Valid @RequestBody ApplicationConfigurationDTO applicationConfigurationDTO) {
        log.debug("REST request to update ApplicationConfiguration : {}", applicationConfigurationDTO);
        if (applicationConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationConfigurationDTO result = applicationConfigurationService.save(applicationConfigurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /application-configurations : get all the applicationConfigurations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of applicationConfigurations in body
     */
    @GetMapping("/application-configurations")
    @Timed
    public ResponseEntity<List<ApplicationConfigurationDTO>> getAllApplicationConfigurations(Pageable pageable) throws BadPaddingException, IllegalBlockSizeException {
        log.debug("REST request to get a page of ApplicationConfigurations");
        Page<ApplicationConfigurationDTO> page = applicationConfigurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys/application-configurations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /application-configurations/:id : get the "id" applicationConfiguration.
     *
     * @param id the id of the applicationConfigurationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationConfigurationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/application-configurations/{id}")
    @Timed
    public ResponseEntity<ApplicationConfigurationDTO> getApplicationConfiguration(@PathVariable Long id) {
        log.debug("REST request to get ApplicationConfiguration : {}", id);
        Optional<ApplicationConfigurationDTO> applicationConfigurationDTO = applicationConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationConfigurationDTO);
    }

    /**
     * GET  /application-configurations/:key : get the "key" applicationConfiguration.
     *
     * @param key the key of the applicationConfigurationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationConfigurationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/application-configurations/key/{key}")
    @Timed
    public ResponseEntity<ApplicationConfigurationDTO> getConfiguration(@PathVariable String key) {
        log.debug("REST request to get ApplicationConfiguration : {}", key);
        Optional<ApplicationConfigurationDTO> applicationConfigurationDTO =
                        applicationConfigurationService.getConfiguration(key);
        return ResponseUtil.wrapOrNotFound(applicationConfigurationDTO);
    }

    /**
     * DELETE  /application-configurations/:id : delete the "id" applicationConfiguration.
     *
     * @param id the id of the applicationConfigurationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/application-configurations/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicationConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationConfiguration : {}", id);
        applicationConfigurationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
