package ae.rta.dls.backend.service.dto.sct;

import ae.rta.dls.backend.domain.enumeration.trn.ApplicationTypeStatus;
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
import javax.persistence.Lob;

/**
 * A DTO for the ApplicationType entity.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationTypeDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    @Type(type = "json")
    //@Convert(converter = MultilingualJsonConverter.class)
    private MultilingualJsonType description;

    @NotNull
    @Lob
    //@Convert(converter = MultilingualJsonConverter.class)
    @Type(type = "json")
    private MultilingualJsonType summary;

    @NotNull
    private ApplicationTypeStatus status;

    private Integer sortOrder;

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

    public MultilingualJsonType getDescription() {
        return description;
    }

    public void setDescription(MultilingualJsonType description) {
        this.description = description;
    }

    public MultilingualJsonType getSummary() {
        return summary;
    }

    public void setSummary(MultilingualJsonType summary) {
        this.summary = summary;
    }

    public ApplicationTypeStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationTypeStatus status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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

        ApplicationTypeDTO applicationTypeDTO = (ApplicationTypeDTO) o;
        if (applicationTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationTypeDTO{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", description='" + getDescription() + "'" +
            ", summary='" + getSummary() + "'" +
            ", status=" + getStatus() +
            ", sortOrder=" + getSortOrder() +
            "}";
    }
}
