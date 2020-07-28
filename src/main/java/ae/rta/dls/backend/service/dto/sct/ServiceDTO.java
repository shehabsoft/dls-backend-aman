package ae.rta.dls.backend.service.dto.sct;


import ae.rta.dls.backend.domain.enumeration.trn.ServiceStatus;
import ae.rta.dls.backend.domain.util.MultilingualJsonConverter;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Convert;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Service entity.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    @JsonProperty("code")
    private Long code;

    @NotNull
    @Type(type = "json")
    //@Convert(converter = MultilingualJsonConverter.class)
    @JsonProperty("name")
    private MultilingualJsonType name;

    @NotNull
    private ServiceStatus status;

    private Long applicationTypeId;

    private Long serviceGroupId;

    private String serviceImplClass;

    private String serviceDTOClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public MultilingualJsonType getName() {
        return name;
    }

    public void setName(MultilingualJsonType name) {
        this.name = name;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public Long getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(Long applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public Long getServiceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(Long serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }

    public String getServiceImplClass() {
        return serviceImplClass;
    }

    public void setServiceImplClass(String serviceImplClass) {
        this.serviceImplClass = serviceImplClass;
    }

    public String getServiceDTOClass() {
        return serviceDTOClass;
    }

    public void setServiceDTOClass(String serviceDTOClass) {
        this.serviceDTOClass = serviceDTOClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceDTO serviceDTO = (ServiceDTO) o;
        if (serviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceDTO{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", name='" + getName() + "'" +
            ", status=" + getStatus() +
            ", applicationType=" + getApplicationTypeId() +
            ", serviceGroup=" + getServiceGroupId() +
            ", serviceImplClass=" + getServiceImplClass() +
            ", serviceDTOClass=" + getServiceDTOClass() +
            "}";
    }
}
