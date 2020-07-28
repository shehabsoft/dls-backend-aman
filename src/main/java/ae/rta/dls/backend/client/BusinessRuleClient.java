package ae.rta.dls.backend.client;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.brm.calculation.fee.FeeCalculationRequest;
import ae.rta.dls.backend.common.util.brm.validation.RuleValidationRequest;
import ae.rta.dls.backend.common.util.brm.validation.RuleValidationResponse;
import ae.rta.dls.backend.domain.type.FeeDocumentJsonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BusinessRuleClient {

    @Autowired
    private RestTemplate brmRestTemplate;

    @Value("#{environment['brm.context.root']}")
    private String brmContextRoot;

    private final Logger log = LoggerFactory.getLogger(BusinessRuleClient.class);

    /**
     * Check Rules
     *
     * @param ruleValidationRequest Operation Request
     * @return Check Rules Operation Response
     */
    public ResponseEntity<RuleValidationResponse> checkRules(RuleValidationRequest ruleValidationRequest) {
        String url = brmContextRoot + "/DecisionService/rest/RuleValidationService/checkRules";
        return brmRestTemplate.postForEntity(url, ruleValidationRequest, RuleValidationResponse.class);
    }

    /**
     * Calculate Fees
     *
     * @param request Fee Calculation Request
     * @return feeCalculationResponse : FeeCalculationResponse
     */
    public ResponseEntity<FeeDocumentJsonType> calculateFees(FeeCalculationRequest request) {
        try {
            log.debug(">>>>>>>> Calling calculateFees >>>>>>>>>>>");
            String url = brmContextRoot + "/DecisionService/rest/FeesCalculationService/feeCalculateService";
            return brmRestTemplate.postForEntity(url, request, FeeDocumentJsonType.class);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }
    }
}
