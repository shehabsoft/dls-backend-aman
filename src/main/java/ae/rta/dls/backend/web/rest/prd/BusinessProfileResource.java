package ae.rta.dls.backend.web.rest.prd;
import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import ae.rta.dls.backend.service.prd.BusinessProfileService;
import ae.rta.dls.backend.service.prd.LearningFileService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.prd.BusinessProfileDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing BusinessProfile.
 */
@RestController
@RequestMapping("/api/prd")
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class BusinessProfileResource {

    private final Logger log = LoggerFactory.getLogger(BusinessProfileResource.class);

    private static final String ENTITY_NAME = "dlsBackendBusinessProfile";

    private final BusinessProfileService businessProfileService;

    @Autowired
    private LearningFileService learningFileService;

    public BusinessProfileResource(BusinessProfileService businessProfileService) {
        this.businessProfileService = businessProfileService;
    }

    /**
     * POST  /business-profiles : Create a new businessProfile.
     *
     * @param businessProfileDTO the businessProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessProfileDTO, or with status 400 (Bad Request) if the businessProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-profiles")
    public ResponseEntity<BusinessProfileDTO> createBusinessProfile(@Valid @RequestBody BusinessProfileDTO businessProfileDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessProfile : {}", businessProfileDTO);
        if (businessProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessProfileDTO result = businessProfileService.save(businessProfileDTO);
        return ResponseEntity.created(new URI("/api/prd/business-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-profiles : Updates an existing businessProfile.
     *
     * @param businessProfileDTO the businessProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessProfileDTO,
     * or with status 400 (Bad Request) if the businessProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the businessProfileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-profiles")
    public ResponseEntity<BusinessProfileDTO> updateBusinessProfile(@Valid @RequestBody BusinessProfileDTO businessProfileDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessProfile : {}", businessProfileDTO);
        if (businessProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessProfileDTO result = businessProfileService.save(businessProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-profiles : get all the businessProfiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of businessProfiles in body
     */
    @GetMapping("/business-profiles")
    public ResponseEntity<List<BusinessProfileDTO>> getAllBusinessProfiles(Pageable pageable) {
        log.debug("REST request to get a page of BusinessProfiles");
        Page<BusinessProfileDTO> page = businessProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business-profiles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /business-profiles/:id : get the "id" businessProfile.
     *
     * @param id the id of the businessProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/business-profiles/{id}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.VERIFIED_ROLE + "\") ")
    public ResponseEntity<BusinessProfileDTO> getBusinessProfile(@PathVariable Long id) {
        log.debug("REST request to get BusinessProfile : {}", id);

        Optional<BusinessProfileDTO> businessProfileDTO = businessProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessProfileDTO);
    }

    /**
     * GET  business-profiles/trafficCodeNo/{trafficCodeNo}.
     *
     * @param trafficCodeNo the id of the list businessProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the List businessProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/business-profiles/trafficCodeNo/{trafficCodeNo}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.VERIFIED_ROLE + "\") ")
    public ResponseEntity<BusinessProfileDTO> getByTrafficCodeNo(@PathVariable Long trafficCodeNo) {
        log.debug("REST request to get BusinessProfile by traffic code number: {}", trafficCodeNo);

        Optional<BusinessProfileDTO> businessProfileDTO = businessProfileService.findByTrafficCodeNo(trafficCodeNo);

        return ResponseUtil.wrapOrNotFound(businessProfileDTO);
    }

    /**
     * DELETE  /business-profiles/:id : delete the "id" businessProfile.
     *
     * @param id the id of the businessProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-profiles/{id}")
    public ResponseEntity<Void> deleteBusinessProfile(@PathVariable Long id) {
        log.debug("REST request to delete BusinessProfile : {}", id);
        businessProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
