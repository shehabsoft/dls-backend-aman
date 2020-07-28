package ae.rta.dls.backend.web.rest.errors;

import ae.rta.dls.backend.common.errors.SystemException;
import org.zalando.problem.Status;

import javax.validation.constraints.NotEmpty;

public class NotImplementedException extends SystemException {

    private static final long serialVersionUID = 1L;

    public NotImplementedException(@NotEmpty String feature) {
        super("No implementation found for this feature: " + feature, Status.NOT_IMPLEMENTED);
    }
}
