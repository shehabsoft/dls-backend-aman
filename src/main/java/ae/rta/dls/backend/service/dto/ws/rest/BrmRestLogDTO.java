package ae.rta.dls.backend.service.dto.ws.rest;

import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the BrmRestLog entity.
 */
public class BrmRestLogDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String correlationId;

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

        BrmRestLogDTO brmRestLogDTO = (BrmRestLogDTO) o;
        if (brmRestLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), brmRestLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BrmRestLogDTO{" +
            "id=" + getId() +
            ", correlationId='" + getCorrelationId() + "'" +
            ", httpMethod='" + getHttpMethod() + "'" +
            ", httpStatus=" + getHttpStatus() +
            ", requestUrl='" + getRequestUrl() + "'" +
            ", requestBody='" + getRequestBody() + "'" +
            ", responseBody='" + getResponseBody() + "'" +
            "}";
    }
}
