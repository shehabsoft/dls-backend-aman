package ae.rta.dls.backend.web.rest.errors;

import ae.rta.dls.backend.web.rest.util.ErrorConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BadRequestAlertException(String defaultMessage) {
        super(ErrorConstants.DEFAULT_TYPE, defaultMessage, Status.BAD_REQUEST, null, null, null, null);
    }

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        super(ErrorConstants.DEFAULT_TYPE, defaultMessage, Status.BAD_REQUEST, null, null, null, null);
    }
}
