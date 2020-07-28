package ae.rta.dls.backend.domain.ws.rest;

import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Rest Log entity.
 * 
 * @author Rami Nassar
 */
@ApiModel(description = "Rest Log entity. @author Rami Nassar")
@Entity
@Table(name = "ws_rest_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestLog extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reloSequenceGenerator")
    @SequenceGenerator(name = "reloSequenceGenerator", sequenceName = "relo_seq", allocationSize = 1)
    private Long id;

    @Column(name = "correlation_id")
    private String correlationId;

    @Column(name = "application_id")
    private Long applicationId;

    @NotNull
    @Column(name = "rest_mode", nullable = false)
    private Integer restMode;

    @NotNull
    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    @Column(name = "http_status")
    private Integer httpStatus;

    @NotNull
    @Column(name = "request_url", nullable = false)
    private String requestUrl;

    @Lob
    @Column(name = "request_body")
    private String requestBody;

    @Lob
    @Column(name = "response_body")
    private String responseBody;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public RestLog correlationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Integer getRestMode() {
        return restMode;
    }

    public RestLog restMode(Integer restMode) {
        this.restMode = restMode;
        return this;
    }

    public void setRestMode(Integer restMode) {
        this.restMode = restMode;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public RestLog httpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public RestLog httpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public RestLog requestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public RestLog requestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public RestLog responseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public RestLog applicationId(Long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestLog restLog = (RestLog) o;
        if (restLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RestLog{" +
            "id=" + getId() +
            ", correlationId='" + getCorrelationId() + "'" +
            ", applicationId='" + getApplicationId() + "'" +
            ", restMode='" + getRestMode() + "'" +
            ", httpMethod='" + getHttpMethod() + "'" +
            ", httpStatus=" + getHttpStatus() +
            ", requestUrl='" + getRequestUrl() + "'" +
            ", requestBody='" + getRequestBody() + "'" +
            ", responseBody='" + getResponseBody() + "'" +
            "}";
    }
}
