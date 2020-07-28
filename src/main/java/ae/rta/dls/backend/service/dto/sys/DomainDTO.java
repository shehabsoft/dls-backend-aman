package ae.rta.dls.backend.service.dto.sys;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Domain entity.
 */
@ApiModel(description = "The Domain entity. @author Mena Emiel.")
@JsonPropertyOrder({"id", "code", "description", "domainValues"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DomainDTO extends AbstractAuditingDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("code")
    private String code;

    @NotNull
    @JsonProperty("description")
    private MultilingualJsonType description;

    @JsonProperty("domainValues")
    private Set<DomainValueDTO> domainValues = new HashSet<>();


    /**
     * A two sides relationship
     */
    @ApiModelProperty(value = "A two sides relationship")

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

    public Set<DomainValueDTO> getDomainValues() {
        return domainValues;
    }

    public void setDomainValues(Set<DomainValueDTO> domainValues) {
        this.domainValues = domainValues;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DomainDTO domainDTO = (DomainDTO) o;
        if (domainDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), domainDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DomainDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
