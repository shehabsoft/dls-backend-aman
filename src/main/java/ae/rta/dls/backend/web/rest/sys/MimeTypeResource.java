package ae.rta.dls.backend.web.rest.sys;
import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import ae.rta.dls.backend.service.sys.MimeTypeService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.sys.MimeTypeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MimeType.
 */
@RestController
@RequestMapping("/api/sys")
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class MimeTypeResource {

    private final Logger log = LoggerFactory.getLogger(MimeTypeResource.class);

    private static final String ENTITY_NAME = "mimeType";

    private final MimeTypeService mimeTypeService;

    public MimeTypeResource(MimeTypeService mimeTypeService) {
        this.mimeTypeService = mimeTypeService;
    }

    /**
     * POST  /mime-types : Create a new mimeType.
     *
     * @param mimeTypeDTO the mimeTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mimeTypeDTO, or with status 400 (Bad Request) if the mimeType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mime-types")
    public ResponseEntity<MimeTypeDTO> createMimeType(@RequestBody MimeTypeDTO mimeTypeDTO) throws URISyntaxException {
        log.debug("REST request to save MimeType : {}", mimeTypeDTO);
        if (mimeTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new mimeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MimeTypeDTO result = mimeTypeService.save(mimeTypeDTO);
        return ResponseEntity.created(new URI("/api/sys/mime-types/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /mime-types : Updates an existing mimeType.
     *
     * @param mimeTypeDTO the mimeTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mimeTypeDTO,
     * or with status 400 (Bad Request) if the mimeTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the mimeTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mime-types")
    public ResponseEntity<MimeTypeDTO> updateMimeType(@RequestBody MimeTypeDTO mimeTypeDTO) throws URISyntaxException {
        log.debug("REST request to update MimeType : {}", mimeTypeDTO);
        if (mimeTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MimeTypeDTO result = mimeTypeService.save(mimeTypeDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mimeTypeDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /mime-types : get all the mimeTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mimeTypes in body
     */
    @GetMapping("/mime-types/pageable")
    public ResponseEntity<List<MimeTypeDTO>> getAllMimeTypes(Pageable pageable) {
        log.debug("REST request to get a page of MimeTypes");
        Page<MimeTypeDTO> page = mimeTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys/mime-types/pageable");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /mime-types/:id : get the "id" mimeType.
     *
     * @param id the id of the mimeTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mimeTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mime-types/{id}")
    public ResponseEntity<MimeTypeDTO> getMimeType(@PathVariable Long id) {
        log.debug("REST request to get MimeType : {}", id);
        Optional<MimeTypeDTO> mimeTypeDTO = mimeTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mimeTypeDTO);
    }

    /**
     * DELETE  /mime-types/:id : delete the "id" mimeType.
     *
     * @param id the id of the mimeTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mime-types/{id}")
    public ResponseEntity<Void> deleteMimeType(@PathVariable Long id) {
        log.debug("REST request to delete MimeType : {}", id);
        mimeTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
