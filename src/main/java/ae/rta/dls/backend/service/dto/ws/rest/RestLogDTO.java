package ae.rta.dls.backend.service.dto.ws.rest;

import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RestLog entity.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestLogDTO extends AbstractAuditingDTO implements Serializable {

    /**
     * Enum for the Configuration Keys
     */
    public enum RestMode {
        IN(1),
        OUT(2);

        private Integer value;

        RestMode(final Integer value) {
            this.value = value;
        }

        public Integer value() {
            return value;
        }
    }

    private Long id;

    private String correlationId;

    private Long applicationId;

    @NotNull
    private Integer restMode;

    @NotNull
    private String httpMethod;

    private Integer httpStatus;

    @NotNull
    private String requestUrl;

    @Lob
    private String requestBody;

    @Lob
    private String responseBody;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getRestMode() {
        return restMode;
    }

    public void setRestMode(Integer restMode) {
        this.restMode = restMode;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RestLogDTO restLogDTO = (RestLogDTO) o;
        if (restLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RestLogDTO{" +
            "id=" + getId() +
            ", correlationId='" + getCorrelationId() + "'" +
            ", restMode='" + getRestMode() + "'" +
            ", httpMethod='" + getHttpMethod() + "'" +
            ", httpStatus=" + getHttpStatus() +
            ", requestUrl='" + getRequestUrl() + "'" +
            ", requestBody='" + getRequestBody() + "'" +
            ", responseBody='" + getResponseBody() + "'" +
            "}";
    }
}
