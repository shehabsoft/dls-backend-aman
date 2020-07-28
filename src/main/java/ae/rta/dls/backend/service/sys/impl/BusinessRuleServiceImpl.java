package ae.rta.dls.backend.service.sys.impl;

import ae.rta.dls.backend.client.BusinessRuleClient;
import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.brm.calculation.fee.FeeCalculationRequest;
import ae.rta.dls.backend.common.util.brm.validation.RuleValidationRequest;
import ae.rta.dls.backend.common.util.brm.validation.RuleValidationResponse;
import ae.rta.dls.backend.domain.type.FeeDocumentJsonType;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.sys.BusinessRuleService;
import ae.rta.dls.backend.service.trn.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The Business Rule Service Implementation.
 * @author Tariq Abu Amireh
 */
@Service
public class BusinessRuleServiceImpl implements BusinessRuleService {

    private final Logger log = LoggerFactory.getLogger(BusinessRuleServiceImpl.class);

    @Autowired
    private BusinessRuleClient businessRuleClient;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public RuleValidationResponse checkRules(RuleValidationRequest request) {
        String debugMsg = String.format("Activating user for activation key %s", request.toString());
        log.debug(debugMsg);

        ResponseEntity<RuleValidationResponse> response = businessRuleClient.checkRules(request);

        return response.getBody();
    }

    @Override
    public FeeDocumentJsonType calculateFees(FeeCalculationRequest request) {
        String debugMsg = String.format("Calculate Fees %s", request.toString());
        log.debug(debugMsg);

        // Set tcnFeesCollected flag
        Optional<ApplicationDTO> application = applicationService.findOne(request.getApplicationRef());

        if(!application.isPresent()) {
            log.error("Invalid application details with application id {}", request.getApplicationRef());
            throw new SystemException("Invalid application id");
        }

        request.setTcnFeesCollected(application.get().getApplicationCriteria().getTcnFeesCollected());

        ResponseEntity<FeeDocumentJsonType> response = businessRuleClient.calculateFees(request);

        return response.getBody();
    }

}
