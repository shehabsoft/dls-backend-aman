package ae.rta.dls.backend.service.dto.sys;

import ae.rta.dls.backend.common.util.NumberUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

/**
 * A DTO for the ErrorLog DTO.
 */
@ApiModel(description = "The ErrorLog entity. @author Mohammad Qasim.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorLogDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    private String source;

    private String cause;

    private String message;

    private Long applicationId;

    /**
     * Default constructor
     */
    public ErrorLogDTO() {
        this.source = "";
    }


    /**
     * Error Log constructor to prepare the DTO
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    public ErrorLogDTO(JoinPoint joinPoint, Throwable e, HttpServletRequest httpRequest) {
        final Logger log = LoggerFactory.getLogger(ErrorLogDTO.class);
        this.source = "";
        try {
            log.debug("Start constructing error log object");
            Optional<JoinPoint> optionalJointPoint = Optional.ofNullable(joinPoint);
            Optional<Throwable> optionalException = Optional.ofNullable(e);

            if (optionalJointPoint.isPresent()) {
                this.source = (optionalJointPoint.get().getSignature().getDeclaringTypeName()).concat(".")
                    .concat(optionalJointPoint.get().getSignature().getName().concat("()"));
            }
            if (optionalException.isPresent()) {
                this.cause = optionalException.get().getCause() != null ? optionalException.get().getCause().toString() : "NULL";
                this.message = optionalException.get().getMessage() == null ? "Message:[ Empty Message ] Stack Trace:[ "
                    .concat(ExceptionUtils.getStackTrace(e)) : ("Message:[ ").concat(optionalException.get().getMessage()).concat(" ] ").concat("Stack Trace:[ ").concat(ExceptionUtils.getStackTrace(e));
            }
            if (httpRequest != null && !StringUtil.isBlank(httpRequest.getHeader("applicationRefNo")) &&
                NumberUtil.toLong(httpRequest.getHeader("applicationRefNo")) != 0) {

                this.applicationId = NumberUtil.toLong(httpRequest.getHeader("applicationRefNo"));
            }
        } catch (Exception ex) {
            log.error("Exception while constructing error log object {}", ex);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorLogDTO errorLog = (ErrorLogDTO) o;
        if (errorLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), errorLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ErrorLogDTO{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", cause='" + getCause() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
