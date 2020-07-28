package ae.rta.dls.backend.web.rest.errors;

import ae.rta.dls.backend.common.errors.SystemException;

public class NullValueException extends SystemException {

    private static final long serialVersionUID = 1L;

    public NullValueException(String fieldName) {
        super("Null " + fieldName + " value!");
    }
}
