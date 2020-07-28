package ae.rta.dls.backend.web.rest.errors;

import ae.rta.dls.backend.web.rest.util.ErrorConstants;
import org.zalando.problem.Status;
import javax.validation.constraints.NotEmpty;
import java.net.URI;

/**
 * This class used to wrap validation exceptions.
 *
 * @author Yousef Abu Amireh
 * @version 1.00
 */
public class ValidationException extends RuntimeException {
    /** Serialization version ID. */
    @SuppressWarnings("compatibility:6476992599168662163")
    private static final long serialVersionUID = -4105396575047568521L;

    /*
     * Instance variables
     */

    private final String messageKey;

    private final String[] parameters;

    private final String type;

    private final Status status;

    private final URI redirectionUri;

    private final ValidationType validationType;

    /*
     * Constructors
     */

    /**
     * Construct new SystemException object.
     *
     * @param messageKey Exception message.
     */
    public ValidationException(@NotEmpty String messageKey) {
        this(messageKey, ErrorConstants.TYPE_VALIDATION, Status.ACCEPTED,ValidationType.ERROR);
    }
    /**
     * Construct new SystemException object.
     *
     * @param messageKey Exception message.
     */
    public ValidationException(@NotEmpty String messageKey,ValidationType validationType) {
        this(messageKey, ErrorConstants.TYPE_VALIDATION, Status.ACCEPTED,validationType);
    }

    public ValidationException(@NotEmpty String messageKey, String... params) {
        this(messageKey, ErrorConstants.TYPE_VALIDATION, Status.ACCEPTED, null,ValidationType.ERROR, params);
    }

    public ValidationException(@NotEmpty String messageKey,ValidationType validationType, String... params) {
        this(messageKey, ErrorConstants.TYPE_VALIDATION, Status.ACCEPTED, null,validationType, params);
    }

    public ValidationException(@NotEmpty String messageKey, @NotEmpty String type, @NotEmpty Status status,ValidationType validationType) {
        this(messageKey, type, status, null,validationType);
    }

    public ValidationException(@NotEmpty String messageKey, @NotEmpty String type, URI recirectionUri) {
        this(messageKey, type, Status.ACCEPTED, recirectionUri, null);
    }

    public ValidationException(@NotEmpty String messageKey, @NotEmpty String type, @NotEmpty Status status, URI recirectionUri,ValidationType validationType, String... parameters) {
        this.messageKey = messageKey;
        this.type = type;
        this.status = status;
        this.redirectionUri = recirectionUri;
        this.validationType = validationType;
        this.parameters = parameters;
    }

    /**
     * Overrides toString() method.
     *
     * @return String representation of this exception.
     */
    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();

        // Add this exception message if exists
        if (super.toString() != null) {
            msg.append(super.toString());
        }

        return msg.toString();
    }

    /**
     * Overrides getMessage() to append the nested Exception message.
     *
     * @return Nested exception message.
     */
    @Override
    public String getMessage() {
        // Create message StringBuffer
        StringBuilder msg = new StringBuilder();

        // Append whatever message was passed into the constructor
        if (super.getMessage() != null) {
            msg.append(super.getMessage());
        }

        // return message contents
        return msg.toString();
    }

    public Object[] getParameters() {
        return parameters;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    public URI getRedirectionUri() {
        return redirectionUri;
    }

    public ValidationType getValidationType() {
        return validationType;
    }

    public void setValidationType(StackTraceElement[] stackTrace) {
        super.setStackTrace(stackTrace);
    }
}
