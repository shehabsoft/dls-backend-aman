package ae.rta.dls.backend.service.sys;

import ae.rta.dls.backend.common.util.brm.calculation.fee.FeeCalculationRequest;
import ae.rta.dls.backend.common.util.brm.validation.RuleValidationRequest;
import ae.rta.dls.backend.common.util.brm.validation.RuleValidationResponse;
import ae.rta.dls.backend.domain.type.FeeDocumentJsonType;

/**
 * The Business Rule Service.
 * @author Tariq Abu Amireh
 */
public interface BusinessRuleService {

    /**
     * Check Business Rules
     *
     * @param request : Operation Request
     *
     * @return Operation Response
     */
    RuleValidationResponse checkRules(RuleValidationRequest request);

    /**
     * Calculate Fees
     *
     * @param request : Fee Calculation Request
     *
     * @return Fee Calculation Response
     */
    FeeDocumentJsonType calculateFees(FeeCalculationRequest request);
}
