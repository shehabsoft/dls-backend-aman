package ae.rta.dls.backend.web.rest.errors;

import ae.rta.dls.backend.web.rest.util.ErrorConstants;

public class ApplicationNotFoundException extends ValidationException {

    private static final long serialVersionUID = 1L;

    public ApplicationNotFoundException() {
        super("error.application.notFound", ErrorConstants.TYPE_APPLICATION_NOT_FOUND, ErrorConstants.APPLICATION_NOT_FOUND_URI);
    }
}
