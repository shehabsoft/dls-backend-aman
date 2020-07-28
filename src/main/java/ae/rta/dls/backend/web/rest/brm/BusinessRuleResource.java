package ae.rta.dls.backend.web.rest.brm;

import ae.rta.dls.backend.common.util.brm.calculation.fee.FeeCalculationRequest;
import ae.rta.dls.backend.common.util.brm.validation.RuleValidationRequest;
import ae.rta.dls.backend.common.util.brm.validation.RuleValidationResponse;
import ae.rta.dls.backend.domain.type.FeeDocumentJsonType;
import ae.rta.dls.backend.service.sys.BusinessRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller for managing BRM actions
 * @Auther Tariq Abu Amireh
 */
@RestController
@RequestMapping("/api/brm")
public class BusinessRuleResource {

    private final Logger log = LoggerFactory.getLogger(BusinessRuleResource.class);

    private final BusinessRuleService businessRuleService;

    public BusinessRuleResource(BusinessRuleService businessRuleService) {
        this.businessRuleService = businessRuleService;
    }

    @PostMapping("/calculate-fees")
    public FeeDocumentJsonType calculateFees(@Valid @RequestBody FeeCalculationRequest feeCalculationRequest) {
        log.debug("REST request to get Calculate service fee : {}", feeCalculationRequest);

        return businessRuleService.calculateFees(feeCalculationRequest);
    }

    @PostMapping("/check-rules")
    public RuleValidationResponse checkRules(@Valid @RequestBody RuleValidationRequest ruleValidationRequest) {
        log.debug("REST request to check rules : {}", ruleValidationRequest);

        return businessRuleService.checkRules(ruleValidationRequest);
    }
}
