package ae.rta.dls.backend.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessRuleAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final transient Map<String, Object> parameters;

    public BusinessRuleAlertException(List<BusinessRuleVM> businessRuleVmList) {

        Map<String, Object> params = new HashMap();
        params.put("violations", businessRuleVmList);
        this.parameters = params;
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }
}
