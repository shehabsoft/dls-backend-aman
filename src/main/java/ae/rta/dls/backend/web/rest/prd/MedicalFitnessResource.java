package ae.rta.dls.backend.web.rest.prd;
import ae.rta.dls.backend.service.prd.MedicalFitnessService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.prd.MedicalFitnessDTO;
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
 * REST controller for managing MedicalFitness.
 */
@RestController
@RequestMapping("/api/prd")
public class MedicalFitnessResource {

    private final Logger log = LoggerFactory.getLogger(MedicalFitnessResource.class);

    private static final String ENTITY_NAME = "dlsBackendMedicalFitness";

    private final MedicalFitnessService medicalFitnessService;

    public MedicalFitnessResource(MedicalFitnessService medicalFitnessService) {
        this.medicalFitnessService = medicalFitnessService;
    }

    /**
     * POST  /medical-fitnesses : Create a new medicalFitness.
     *
     * @param medicalFitnessDTO the medicalFitnessDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalFitnessDTO, or with status 400 (Bad Request) if the medicalFitness has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-fitnesses")
    public ResponseEntity<MedicalFitnessDTO> createMedicalFitness(@Valid @RequestBody MedicalFitnessDTO medicalFitnessDTO) throws URISyntaxException {
        log.debug("REST request to save MedicalFitness : {}", medicalFitnessDTO);
        if (medicalFitnessDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalFitness cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalFitnessDTO result = medicalFitnessService.save(medicalFitnessDTO);
        return ResponseEntity.created(new URI("/api/prd/medical-fitnesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-fitnesses : Updates an existing medicalFitness.
     *
     * @param medicalFitnessDTO the medicalFitnessDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalFitnessDTO,
     * or with status 400 (Bad Request) if the medicalFitnessDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicalFitnessDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-fitnesses")
    public ResponseEntity<MedicalFitnessDTO> updateMedicalFitness(@Valid @RequestBody MedicalFitnessDTO medicalFitnessDTO) throws URISyntaxException {
        log.debug("REST request to update MedicalFitness : {}", medicalFitnessDTO);
        if (medicalFitnessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalFitnessDTO result = medicalFitnessService.save(medicalFitnessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalFitnessDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-fitnesses : get all the medicalFitnesses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medicalFitnesses in body
     */
    @GetMapping("/medical-fitnesses")
    public ResponseEntity<List<MedicalFitnessDTO>> getAllMedicalFitnesses(Pageable pageable) {
        log.debug("REST request to get a page of MedicalFitnesses");
        Page<MedicalFitnessDTO> page = medicalFitnessService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prd/medical-fitnesses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /medical-fitnesses/:id : get the "id" medicalFitness.
     *
     * @param id the id of the medicalFitnessDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalFitnessDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medical-fitnesses/{id}")
    public ResponseEntity<MedicalFitnessDTO> getMedicalFitness(@PathVariable Long id) {
        log.debug("REST request to get MedicalFitness : {}", id);
        Optional<MedicalFitnessDTO> medicalFitnessDTO = medicalFitnessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalFitnessDTO);
    }

    /**
     * DELETE  /medical-fitnesses/:id : delete the "id" medicalFitness.
     *
     * @param id the id of the medicalFitnessDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-fitnesses/{id}")
    public ResponseEntity<Void> deleteMedicalFitness(@PathVariable Long id) {
        log.debug("REST request to delete MedicalFitness : {}", id);
        medicalFitnessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
