package ae.rta.dls.backend.web.rest.sdm;

import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import ae.rta.dls.backend.service.sdm.LicenseCategoryService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.sdm.LicenseCategoryDTO;
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
 * REST controller for managing LicenseCategory.
 */
@RestController
@RequestMapping("/api/sdm")
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class LicenseCategoryResource {

    private final Logger log = LoggerFactory.getLogger(LicenseCategoryResource.class);

    private static final String ENTITY_NAME = "dlsBackendLicenseCategory";

    private final LicenseCategoryService licenseCategoryService;

    public LicenseCategoryResource(LicenseCategoryService licenseCategoryService) {
        this.licenseCategoryService = licenseCategoryService;
    }

    /**
     * POST  /license-categories : Create a new licenseCategory.
     *
     * @param licenseCategoryDTO the licenseCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new licenseCategoryDTO, or with status 400 (Bad Request) if the licenseCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/license-categories")
    public ResponseEntity<LicenseCategoryDTO> createLicenseCategory(@Valid @RequestBody LicenseCategoryDTO licenseCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save LicenseCategory : {}", licenseCategoryDTO);
        if (licenseCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new licenseCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LicenseCategoryDTO result = licenseCategoryService.save(licenseCategoryDTO);
        return ResponseEntity.created(new URI("/api/sdm/license-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /license-categories : Updates an existing licenseCategory.
     *
     * @param licenseCategoryDTO the licenseCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated licenseCategoryDTO,
     * or with status 400 (Bad Request) if the licenseCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the licenseCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/license-categories")
    public ResponseEntity<LicenseCategoryDTO> updateLicenseCategory(@Valid @RequestBody LicenseCategoryDTO licenseCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update LicenseCategory : {}", licenseCategoryDTO);
        if (licenseCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LicenseCategoryDTO result = licenseCategoryService.save(licenseCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, licenseCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /license-categories : get all the licenseCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of licenseCategories in body
     */
    @GetMapping("/license-categories")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\")")
    public ResponseEntity<List<LicenseCategoryDTO>> getAllLicenseCategories(Pageable pageable) {
        log.debug("REST request to get a page of LicenseCategories");
        Page<LicenseCategoryDTO> page = licenseCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sdm/license-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /license-categories/:countryCode : get the "countryCode" licenseCategory.
     *
     * @param countryCode the countryCode of the licenseCategoryDTO to retrieve
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body the licenseCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/license-categories/country/{countryCode}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\") || hasRole(\"" + AuthoritiesConstants.VERIFIED_ROLE + "\")")
    public ResponseEntity<List<LicenseCategoryDTO>> getLicenseCategory(@PathVariable String countryCode, Pageable pageable) {
        log.debug("REST request to get LicenseCategory countryCode : {}", countryCode);
        Page<LicenseCategoryDTO> page = licenseCategoryService.findActiveLicenseCategoriesByCountry(countryCode.trim(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sdm/license-categories/country");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET /license-categories/exchangeable/:countryCode : get the "countryCode" exchangeable licenseCategory..
     *
     * @param countryCode the countryCode of the licenseCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the licenseCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/license-categories/country/exchangeable/{countryCode}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\") || hasRole(\"" + AuthoritiesConstants.VERIFIED_ROLE + "\")")
    public ResponseEntity<List<LicenseCategoryDTO>> getExchangeableLicenseCategories (@PathVariable String countryCode) {
        log.debug("REST request to get LicenseCategory countryCode : {}", countryCode);
        List<LicenseCategoryDTO> licenseCategories = licenseCategoryService.findExchangeableCategoriesByCountry(countryCode.trim());
        return ResponseEntity.ok().body(licenseCategories);
    }

    /**
     * GET /license-categories/exchangeable/:cityCode : get the "cityCode" exchangeable licenseCategory..
     *
     * @param cityCode the cityCode of the licenseCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the licenseCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/license-categories/city/exchangeable/{cityCode}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\") || hasRole(\"" + AuthoritiesConstants.VERIFIED_ROLE + "\")")
    public ResponseEntity<List<LicenseCategoryDTO>> getExchangeableLicenseCategoriesByCity (@PathVariable String cityCode) {
        log.debug("REST request to get LicenseCategory cityCode : {}", cityCode);
        List<LicenseCategoryDTO> licenseCategories = licenseCategoryService.findExchangeableCategoriesByCity(cityCode.trim());
        return ResponseEntity.ok().body(licenseCategories);
    }

    /**
     * GET  /license-categories/:id : get the "id" licenseCategory.
     *
     * @param id the id of the licenseCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the licenseCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/license-categories/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\")")
    public ResponseEntity<LicenseCategoryDTO> getLicenseCategory(@PathVariable Long id) {
        log.debug("REST request to get LicenseCategory : {}", id);
        Optional<LicenseCategoryDTO> licenseCategoryDTO = licenseCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(licenseCategoryDTO);
    }

    /**
     * Get /license-categories/:utsMappingCode : get the "id" licenseCategory.
     *
     * @param utsMappingCode : UTS mapping code
     * @return the ResponseEntity with status 200 (OK) and with body the licenseCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/license-categories/utsMappingCode/{utsMappingCode}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "," + AuthoritiesConstants.VERIFIED_ROLE  + "\")")
    public ResponseEntity<LicenseCategoryDTO> getLicenseCategory(@PathVariable Integer utsMappingCode) {
        log.debug("REST request to get LicenseCategory by uts mapping code: {}", utsMappingCode);
        Optional<LicenseCategoryDTO> licenseCategoryDTO = licenseCategoryService.findOne(utsMappingCode);
        return ResponseUtil.wrapOrNotFound(licenseCategoryDTO);
    }

    /**
     * Get /license-categories/:code : get the "id" licenseCategory.
     *
     * @param categoryCode : License Category code
     * @return the ResponseEntity with status 200 (OK) and with body the licenseCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/license-categories/categoryCode/{categoryCode}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.BPM_ROLE+ "\")")
    public ResponseEntity<LicenseCategoryDTO> getLicenseCategoryByCode(@PathVariable String categoryCode) {
        log.debug("REST request to get LicenseCategory by uts mapping code: {}", categoryCode);
        Optional<LicenseCategoryDTO> licenseCategoryDTO = licenseCategoryService.findOne(categoryCode);
        return ResponseUtil.wrapOrNotFound(licenseCategoryDTO);
    }

    /**
     * DELETE  /license-categories/:id : delete the "id" licenseCategory.
     *
     * @param id the id of the licenseCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/license-categories/{id}")
    public ResponseEntity<Void> deleteLicenseCategory(@PathVariable Long id) {
        log.debug("REST request to delete LicenseCategory : {}", id);
        licenseCategoryService.deactivate(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
