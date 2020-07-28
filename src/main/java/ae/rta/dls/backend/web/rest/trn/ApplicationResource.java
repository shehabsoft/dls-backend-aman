package ae.rta.dls.backend.web.rest.trn;

import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.common.Language;
import ae.rta.dls.backend.domain.enumeration.common.SystemType;
import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import ae.rta.dls.backend.service.trn.ApplicationService;
import ae.rta.dls.backend.service.trn.TrnServiceFacade;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.web.rest.vm.trn.ProcessInstanceAppInfoRequestVM;
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
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing Application.
 */
@RestController
@RequestMapping("/api/trn")
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class ApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);

    private static final String ENTITY_NAME = "dlsBackendApplication";

    private final ApplicationService applicationService;

    @Autowired
    private TrnServiceFacade trnServiceFacade;

    public ApplicationResource(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * POST  /applications : Create a new application.
     *
     * @param applicationDTO the applicationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationDTO, or with status 400 (Bad Request) if the application has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/applications")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\")")
    public ResponseEntity<ApplicationDTO> createApplication(@Valid @RequestBody ApplicationDTO applicationDTO) throws URISyntaxException {

        log.debug("REST request to save Application : {}", applicationDTO);

        if (applicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new application cannot already have an ID", ENTITY_NAME, "applicationId.exists");
        }

        ApplicationDTO result = applicationService.create(applicationDTO);

        return ResponseEntity.created(new URI("/api/trn/applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applications : Updates an existing application.
     *
     * @param applicationDTO the applicationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationDTO,
     * or with status 400 (Bad Request) if the applicationDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationDTO couldn't be updated
     */
    @PutMapping("/applications")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "," + AuthoritiesConstants.BPM_ROLE + "," + AuthoritiesConstants.VERIFIED_ROLE + "\") ")
    public ResponseEntity<ApplicationDTO> updateApplication(@Valid @RequestBody ApplicationDTO applicationDTO) {

        log.debug("REST request to update Application : {}", applicationDTO);

        ApplicationDTO result = trnServiceFacade.updateApplication(applicationDTO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applications : Updates an existing application phase.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationDTO,
     * or with status 400 (Bad Request) if the applicationDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/applications/updatePhase")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "," + AuthoritiesConstants.BPM_ROLE + "\") ")
    public ResponseEntity<ApplicationDTO> updateApplicationPhase(@RequestBody ApplicationDTO applicationDTO) {

        if (applicationDTO == null || applicationDTO.getId() == null || applicationDTO.getActivePhase() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "application.id.empty");
        }

        ApplicationDTO result = applicationService.updatePhase(applicationDTO.getId(), applicationDTO.getActivePhase());

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationDTO.getId().toString()))
            .body(result);
    }


    /**
     * GET  /applications : get all the applications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of applications in body
     */
    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationDTO>> getAllApplications(Pageable pageable) {
        log.debug("REST request to get a page of Applications");
        Page<ApplicationDTO> page = applicationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trn/applications");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /applications/:id : get the "id" application.
     *
     * @param id the id of the applicationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/applications/{id}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "," + AuthoritiesConstants.BPM_ROLE + "," + AuthoritiesConstants.VERIFIED_ROLE +  "," + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE  +  "\") ")
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable Long id) {
        log.debug("REST request to get Application : {}", id);
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationDTO);
    }

    /**
     * GET  /applications/applicationTypeRefNo/{applicationTypeRefNo}/userProfileRefNo/{userProfileRefNo} : get the "applicationTypeRefNo" application.
     *
     * @param applicationTypeRefNo the applicationTypeRefNo of the applicationDTO to retrieve
     * @param userProfileRefNo the userProfileRefNo of the applicationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/applications/applicationTypeRefNo/{applicationTypeRefNo}/userProfileRefNo/{userProfileRefNo}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE +","+ AuthoritiesConstants.VERIFIED_ROLE + "\") ")
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable Long applicationTypeRefNo,@PathVariable Long userProfileRefNo) {
        log.debug("REST request to get Application by Application Type Id {}, and UserProfile Id: {}", applicationTypeRefNo,userProfileRefNo);
        Optional<ApplicationDTO> applicationDTO = applicationService.getByUserIdAndApplicationType(applicationTypeRefNo,userProfileRefNo);
        return ResponseUtil.wrapOrNotFound(applicationDTO);
    }

    /**
     * GET  /applications/processInstance/:processInstanceId : get the "processInstanceId" application.
     *
     * @param processInstanceId the Process instance id of the applicationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/applications/processInstance/{processInstanceId}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE + "\") ")
    public ResponseEntity<ApplicationDTO> getApplicationByProcessInstanceId(@PathVariable(value = "processInstanceId") Long processInstanceId) {
        log.debug("REST request to get Application using processInstanceId : {}", processInstanceId);
        Optional<ApplicationDTO> applicationDTO = applicationService.getApplicationByProcessInstanceId(processInstanceId);
        return ResponseUtil.wrapOrNotFound(applicationDTO);
    }

    @PostMapping("/applications/processInstance")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE + "\") ")
    public ResponseEntity<ApplicationDTO> getProcessInstanceApplicationByInfo(@RequestBody ProcessInstanceAppInfoRequestVM processInstanceAppInfoRequestVM) {
        log.debug("REST request to get process instance application by info : {}", processInstanceAppInfoRequestVM);
        Optional<ApplicationDTO> applicationDTO = applicationService.getProcessInstanceApplicationByInfo(processInstanceAppInfoRequestVM);
        return ResponseUtil.wrapOrNotFound(applicationDTO);
    }

    @PostMapping("/applications/info")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE + "\") ")
    public ResponseEntity<Map<String,Object>> getApplicationByInfo(@RequestBody ProcessInstanceAppInfoRequestVM processInstanceAppInfoRequestVM) {
        log.debug("REST request to get process instance application by info : {}", processInstanceAppInfoRequestVM);
        Map<String,Object> applicationsInfo = applicationService.getApplicationsByInfo(processInstanceAppInfoRequestVM);
        return ResponseEntity.ok().body(applicationsInfo);
    }

    /**
     * GET  /applications/applicationTypeId/{applicationTypeId}/mobileNo/{mobileNo}/birthdate/{birthdate} : get the application.
     *
     * @param applicationTypeId application type id
     * @param mobileNo mobile number
     * @param birthdate birthdate
     * @return the ResponseEntity with status 200 (OK) and with body the applicationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/applications/applicationTypeId/{applicationTypeId}/mobileNo/{mobileNo}/birthdate/{birthdate}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\")")
    public ResponseEntity<List<ApplicationDTO>> getApplication(@PathVariable Long applicationTypeId, @PathVariable String mobileNo, @PathVariable String birthdate) {
        log.debug("REST request to get Application by application type id : {} , mobile no : {} , and birthdate : {}", applicationTypeId,mobileNo,birthdate);

        List<ApplicationDTO> applicationList = applicationService.findOne(applicationTypeId , mobileNo, birthdate);
        return ResponseEntity.ok().body(applicationList);
    }

    /**
     * GET  /applications/applicationTypeId/{applicationTypeId}/eidNumber/{eidNumber}/eidExpiryDate/{eidExpiryDate} : get the application.
     *
     * @param applicationTypeId application type id
     * @param eidNumber application type id
     * @param eidExpiryDate mobile number
     * @return the ResponseEntity with status 200 (OK) and with body the applicationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/applications/applicationTypeId/{applicationTypeId}/eidNumber/{eidNumber}/eidExpiryDate/{eidExpiryDate}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\")")
    public ResponseEntity<ApplicationDTO> getApplicationByEmiratesId(@PathVariable Long applicationTypeId, @PathVariable String eidNumber, @PathVariable String eidExpiryDate) {
        log.debug("REST request to get Application by application type id : {}, eidNumber : {} , and eidExpiryDate : {}",applicationTypeId, eidNumber, eidExpiryDate);

        Optional<ApplicationDTO> applicationDTO = applicationService.findOneByEmiratesId(applicationTypeId, eidNumber, eidExpiryDate);
        return ResponseUtil.wrapOrNotFound(applicationDTO);
    }

    /**
     * GET  /applications/applicationReferenceNo/{applicationReferenceNo}/lastEnglishName/{lastEnglishName}
     *
     * @param applicationReferenceNo
     * @param lastEnglishName
     * @return the ResponseEntity with status 200 (OK) and with body the applicationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/applications/applicationReferenceNo/{applicationReferenceNo}/lastEnglishName/{lastEnglishName}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\")")
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable Long applicationReferenceNo, @PathVariable String lastEnglishName) {
        log.debug("REST request to get Application using applicationReferenceNo and lastEnglishName : {}", applicationReferenceNo);
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(applicationReferenceNo);

        if(applicationDTO.isPresent() && !StringUtil.isBlank(applicationDTO.get().getEnglishCustomerName())) {

            String[] customerEnglishName = applicationDTO.get().getEnglishCustomerName().split(" ");
            String lastName = customerEnglishName[customerEnglishName.length-1];

            if(!lastEnglishName.toUpperCase().equalsIgnoreCase(lastName.toUpperCase())) {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseUtil.wrapOrNotFound(applicationDTO);
    }

    /**
     * DELETE  /applications/:id : delete the "id" application.
     *
     * @param id the id of the applicationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/applications/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\")")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.debug("REST request to delete Application : {}", id);
        applicationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
