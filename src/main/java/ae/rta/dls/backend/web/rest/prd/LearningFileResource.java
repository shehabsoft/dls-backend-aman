package ae.rta.dls.backend.web.rest.prd;
import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import ae.rta.dls.backend.service.prd.LearningFileService;
import ae.rta.dls.backend.service.prd.PrdServiceFacade;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.prd.LearningFileDTO;
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
 * REST controller for managing LearningFile.
 */
@RestController
@RequestMapping("/api/prd")
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class LearningFileResource {

    private final Logger log = LoggerFactory.getLogger(LearningFileResource.class);

    private static final String ENTITY_NAME = "dlsBackendLearningFile";

    private final LearningFileService learningFileService;

    @Autowired
    private  PrdServiceFacade  prdServiceFacade;

    public LearningFileResource(LearningFileService learningFileService) {
        this.learningFileService = learningFileService;
    }

    /**
     * POST  /learning-files : Create a new learningFile.
     *
     * @param learningFileDTO the learningFileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new learningFileDTO, or with status 400 (Bad Request) if the learningFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/learning-files")
    public ResponseEntity<LearningFileDTO> createLearningFile(@Valid @RequestBody LearningFileDTO learningFileDTO) throws URISyntaxException {
        log.debug("REST request to save LearningFile : {}", learningFileDTO);
        if (learningFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new learningFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LearningFileDTO result = learningFileService.save(learningFileDTO);
        return ResponseEntity.created(new URI("/api/prd/learning-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /learning-files : Updates an existing learningFile.
     *
     * @param learningFileDTO the learningFileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated learningFileDTO,
     * or with status 400 (Bad Request) if the learningFileDTO is not valid,
     * or with status 500 (Internal Server Error) if the learningFileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/learning-files")
    public ResponseEntity<LearningFileDTO> updateLearningFile(@Valid @RequestBody LearningFileDTO learningFileDTO) throws URISyntaxException {
        log.debug("REST request to update LearningFile : {}", learningFileDTO);
        if (learningFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LearningFileDTO result = learningFileService.save(learningFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, learningFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /learning-files : get all the learningFiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of learningFiles in body
     */
    @GetMapping("/learning-files")
    public ResponseEntity<List<LearningFileDTO>> getAllLearningFiles(Pageable pageable) {
        log.debug("REST request to get a page of LearningFiles");
        Page<LearningFileDTO> page = prdServiceFacade.findAllLearningFile(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prd/learning-files");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /learning-files/:id : get the "id" learningFile.
     *
     * @param id the id of the learningFileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the learningFileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/learning-files/{id}")
    public ResponseEntity<LearningFileDTO> getLearningFile(@PathVariable Long id) {
        log.debug("REST request to get LearningFile : {}", id);
        Optional<LearningFileDTO> learningFileDTO = prdServiceFacade.findLearningFileById(id);
        return ResponseUtil.wrapOrNotFound(learningFileDTO);
    }

    /**
     * GET  /learning-files/:id : get the "id" learningFile.
     *
     * @param applicationReferenceNo the id of the application to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the learningFileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/learning-files/applicationReferenceNo/{applicationReferenceNo}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.VERIFIED_ROLE  + "\")")
    public ResponseEntity<LearningFileDTO> getByApplicationId(@PathVariable Long applicationReferenceNo) {
        log.debug("REST request to get LearningFile by application Id : {}", applicationReferenceNo);
        Optional<LearningFileDTO> learningFileDTO = prdServiceFacade.findLearningFileByApplicationId(applicationReferenceNo);
        return ResponseUtil.wrapOrNotFound(learningFileDTO);
    }

    /**
     * GET  /learning-files/active/:applicationReferenceNo : get active learning file by passing application id.
     *
     * @param applicationReferenceNo the id of the application
     * @return the ResponseEntity with status 200 (OK) and with body the learningFileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/learning-files/activeByApplicationReferenceNo/{applicationReferenceNo}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "," + AuthoritiesConstants.VERIFIED_ROLE  + "\")")
    public ResponseEntity<LearningFileDTO> getActiveLearningFileByApplicationId(@PathVariable Long applicationReferenceNo) {
        log.debug("REST request to get LearningFile by application Id : {}", applicationReferenceNo);
        Optional<LearningFileDTO> learningFileDTO = prdServiceFacade.findActiveLearningFileByApplicationId(applicationReferenceNo);
        return ResponseUtil.wrapOrNotFound(learningFileDTO);
    }

    /**
     * GET  /learning-files/active/:emiratesId : get active learning file by passing emirates Id.
     *
     * @param emiratesId Emirates Id
     * @return the ResponseEntity with status 200 (OK) and with body the learningFileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/learning-files/activeByEmiratesId/{emiratesId}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "," + AuthoritiesConstants.VERIFIED_ROLE  + "\")")
    public ResponseEntity<LearningFileDTO> getActiveLearningFileByEmiratesId(@PathVariable String emiratesId) {
        log.debug("REST request to get LearningFile by emirates Id : {}", emiratesId);
        Optional<LearningFileDTO> learningFileDTO = prdServiceFacade.findActiveLearningFileByEmiratesId(emiratesId);
        return ResponseUtil.wrapOrNotFound(learningFileDTO);
    }

    /**
     * GET  /learning-files/active/:trafficCodeNo : get active learning file by passing traffic file no.
     *
     * @param trafficCodeNo Traffic Code No
     * @return the ResponseEntity with status 200 (OK) and with body the learningFileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/learning-files/active/{trafficCodeNo}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.VERIFIED_ROLE  + "\")")
    public ResponseEntity<LearningFileDTO> getActiveLearningFileByTrafficCodeNo(@PathVariable Long trafficCodeNo) {
        log.debug("REST request to get LearningFile by traffic code number : {}", trafficCodeNo);
        Optional<LearningFileDTO> learningFileDTO = learningFileService.findActiveLearningFileByTrafficCodeNo(trafficCodeNo);
        return ResponseUtil.wrapOrNotFound(learningFileDTO);
    }

    /**
     * DELETE  /learning-files/:id : delete the "id" learningFile.
     *
     * @param id the id of the learningFileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/learning-files/{id}")
    public ResponseEntity<Void> deleteLearningFile(@PathVariable Long id) {
        log.debug("REST request to delete LearningFile : {}", id);
        learningFileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
