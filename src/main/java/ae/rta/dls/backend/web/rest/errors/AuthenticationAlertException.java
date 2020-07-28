package ae.rta.dls.backend.web.rest.errors;

public class AuthenticationAlertException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;

    public AuthenticationAlertException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
