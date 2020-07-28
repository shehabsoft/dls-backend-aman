package ae.rta.dls.backend.service.dto.sys;

import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * A DTO for the EntityAuditConfiguration entity.
 */
@ApiModel(description = "The EntityAuditConfiguration entity. @author Mohammad Qasim.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EntityAuditConfigurationDTO extends AbstractAuditingDTO {

    private Long id;

    @NotNull
    private String entityName;

    @NotNull
    private Boolean needsAudit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Boolean isNeedsAudit() {
        return needsAudit;
    }

    public void setNeedsAudit(Boolean needsAudit) {
        this.needsAudit = needsAudit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntityAuditConfigurationDTO entityAuditConfigurationDTO = (EntityAuditConfigurationDTO) o;
        if (entityAuditConfigurationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entityAuditConfigurationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntityAuditConfigurationDTO {" +
            "id=" + getId() +
            ", entityName=" + getEntityName() +
            ", needsAudit='" + isNeedsAudit() + "'" +
            "}";
    }
}
