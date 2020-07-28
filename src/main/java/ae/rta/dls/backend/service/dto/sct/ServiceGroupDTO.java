package ae.rta.dls.backend.service.dto.sct;


import ae.rta.dls.backend.domain.enumeration.trn.ServiceGroupStatus;
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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the ServiceGroup entity.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceGroupDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    @Type(type = "json")
    //@Convert(converter = MultilingualJsonConverter.class)
    private MultilingualJsonType name;

    @NotNull
    private ServiceGroupStatus status;

    @JsonProperty("services")
    private Set<ServiceDTO> services = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MultilingualJsonType getName() {
        return name;
    }

    public void setName(MultilingualJsonType name) {
        this.name = name;
    }

    public ServiceGroupStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceGroupStatus status) {
        this.status = status;
    }

    public Set<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(Set<ServiceDTO> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceGroupDTO serviceGroupDTO = (ServiceGroupDTO) o;
        if (serviceGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceGroupDTO{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", name='" + getName() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
