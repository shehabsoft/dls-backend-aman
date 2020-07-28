package ae.rta.dls.backend.domain.sys;

import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.Objects;

/**
 * Entity Audit Configuration entity.
 * @author Mena Emiel.
 */
@ApiModel(description = "Entity Audit Configuration entity. @author Mena Emiel.")
@Entity
@Table(name = "sys_entity_audit_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EntityAuditConfiguration extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enacSequenceGenerator")
    @SequenceGenerator(name = "enacSequenceGenerator", sequenceName = "enac_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @NotNull
    @Column(name = "needs_audit", nullable = false)
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

    public EntityAuditConfiguration entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Boolean isNeedsAudit() {
        return needsAudit;
    }

    public EntityAuditConfiguration needsAudit(Boolean needsAudit) {
        this.needsAudit = needsAudit;
        return this;
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
        EntityAuditConfiguration entityAuditConfiguration = (EntityAuditConfiguration) o;
        if (entityAuditConfiguration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entityAuditConfiguration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntityAuditConfiguration{" +
            "id=" + getId() +
            ", entityName='" + getEntityName() + "'" +
            ", needsAudit='" + isNeedsAudit() + "'" +
            "}";
    }
}
