package ae.rta.dls.backend.service.dto.sys;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DomainValue entity.
 */
@ApiModel(description = "The DomainValue entity. @author Mena Emiel.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DomainValueDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String value;

    @NotNull
    @JsonProperty("description")
    private MultilingualJsonType description;

    private Integer sortOrder;

    private Long domainId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MultilingualJsonType getDescription() {
        return description;
    }

    public void setDescription(MultilingualJsonType description) {
        this.description = description;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DomainValueDTO domainValueDTO = (DomainValueDTO) o;
        if (domainValueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), domainValueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DomainValueDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", description='" + getDescription() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", domain=" + getDomainId() +
            "}";
    }
}
