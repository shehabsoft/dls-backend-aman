package ae.rta.dls.backend.web.rest.sdm;

import ae.rta.dls.backend.service.dto.sdm.GlobalLicenseCategoryDTO;
import ae.rta.dls.backend.service.sdm.GlobalLicenseCategoryService;
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
 * REST controller for managing GlobalLicenseCategory.
 */
@RestController
@RequestMapping("/api/sdm")
public class GlobalLicenseCategoryResource {

    private final Logger log = LoggerFactory.getLogger(GlobalLicenseCategoryResource.class);

    private static final String ENTITY_NAME = "dlsBackendGlobalLicenseCategory";

    private final GlobalLicenseCategoryService globalLicenseCategoryService;

    public GlobalLicenseCategoryResource(GlobalLicenseCategoryService globalLicenseCategoryService) {
        this.globalLicenseCategoryService = globalLicenseCategoryService;
    }

    /**
     * POST  /global-license-categories : Create a new globalLicenseCategory.
     *
     * @param globalLicenseCategoryDTO the globalLicenseCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new globalLicenseCategoryDTO, or with status 400 (Bad Request) if the globalLicenseCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/global-license-categories")
    @Timed
    public ResponseEntity<GlobalLicenseCategoryDTO> createGlobalLicenseCategory(@Valid @RequestBody GlobalLicenseCategoryDTO globalLicenseCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save GlobalLicenseCategory : {}", globalLicenseCategoryDTO);
        if (globalLicenseCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new globalLicenseCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlobalLicenseCategoryDTO result = globalLicenseCategoryService.save(globalLicenseCategoryDTO);
        return ResponseEntity.created(new URI("/api/sdm/global-license-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /global-license-categories : Updates an existing globalLicenseCategory.
     *
     * @param globalLicenseCategoryDTO the globalLicenseCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated globalLicenseCategoryDTO,
     * or with status 400 (Bad Request) if the globalLicenseCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the globalLicenseCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/global-license-categories")
    @Timed
    public ResponseEntity<GlobalLicenseCategoryDTO> updateGlobalLicenseCategory(@Valid @RequestBody GlobalLicenseCategoryDTO globalLicenseCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update GlobalLicenseCategory : {}", globalLicenseCategoryDTO);
        if (globalLicenseCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GlobalLicenseCategoryDTO result = globalLicenseCategoryService.save(globalLicenseCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, globalLicenseCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /global-license-categories : get all the globalLicenseCategory.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of globalLicenseCategory in body
     */
    @GetMapping("/global-license-categories")
    @Timed
    public ResponseEntity<List<GlobalLicenseCategoryDTO>> getAllGlobalLicenseCategory(Pageable pageable) {
        log.debug("REST request to get a page of GlobalLicenseCategory");
        Page<GlobalLicenseCategoryDTO> page = globalLicenseCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sdm/global-license-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /global-license-categories/:id : get the "id" globalLicenseCategory.
     *
     * @param id the id of the globalLicenseCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the globalLicenseCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/global-license-categories/{id}")
    @Timed
    public ResponseEntity<GlobalLicenseCategoryDTO> getGlobalLicenseCategory(@PathVariable Long id) {
        log.debug("REST request to get GlobalLicenseCategory : {}", id);
        Optional<GlobalLicenseCategoryDTO> globalLicenseCategoryDTO = globalLicenseCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(globalLicenseCategoryDTO);
    }

    /**
     * DELETE  /global-license-categories/:id : delete the "id" globalLicenseCategory.
     *
     * @param id the id of the globalLicenseCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/global-license-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteGlobalLicenseCategory(@PathVariable Long id) {
        log.debug("REST request to delete GlobalLicenseCategory : {}", id);
        globalLicenseCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
