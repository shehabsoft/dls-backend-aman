package ae.rta.dls.backend.web.rest.prd;

import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import ae.rta.dls.backend.service.dto.prd.HandbookDTO;
import ae.rta.dls.backend.service.prd.HandbookService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * REST controller for managing Handbook.
 */
@RestController
@RequestMapping("/api/prd")
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class HandbookResource {

    private final Logger log = LoggerFactory.getLogger(HandbookResource.class);

    private final HandbookService handbookService;

    public HandbookResource(HandbookService handbookService) {
        this.handbookService = handbookService;
    }

    /**
     * GET  /hand-book/trafficCodeNo/:trafficCodeNo : get handbook file by passing traffic file no.
     *
     * @param trafficCodeNo Traffic Code No
     * @return the ResponseEntity with status 200 (OK) and with body the handbookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/hand-book/trafficCodeNo/{trafficCodeNo}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.VERIFIED_ROLE  + "\")")
    public ResponseEntity<HandbookDTO> getByTrafficCodeNo(@PathVariable Long trafficCodeNo) {
        log.debug("REST request to get handbook by traffic code number : {}", trafficCodeNo);
        Optional<HandbookDTO> handbookDTO = handbookService.findByTrafficCodeNo(trafficCodeNo);
        return ResponseUtil.wrapOrNotFound(handbookDTO);
    }
}
