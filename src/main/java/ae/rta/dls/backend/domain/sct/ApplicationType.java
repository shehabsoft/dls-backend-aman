package ae.rta.dls.backend.domain.sct;

import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import ae.rta.dls.backend.domain.enumeration.trn.ApplicationTypeStatus;
import ae.rta.dls.backend.domain.util.MultilingualJsonConverter;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.vladmihalcea.hibernate.type.json.*;

/**
 * The Application Type entity.
 * @author Tariq Abu Amireh
 */
@ApiModel(description = "The Application Type entity. @author Tariq Abu Amireh")
@Entity
@Table(name = "sct_application_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationType extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aptySequenceGenerator")
    @SequenceGenerator(name = "aptySequenceGenerator", sequenceName = "apty_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Type(type = "json")
    @Column(name = "description", nullable = false)
    //@Convert(converter = MultilingualJsonConverter.class)
    private MultilingualJsonType description;

    @NotNull
    @Type(type = "json")
    @Column(name = "summary", nullable = false)
    //@Convert(converter = MultilingualJsonConverter.class)
    private MultilingualJsonType summary;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationTypeStatus status;

    @Column(name = "sort_order")
    private Integer sortOrder;

    /**
     * A two sides relationship
     */
    @ApiModelProperty(value = "A two sides relationship")
    @OneToMany(mappedBy = "applicationType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Service> services = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public ApplicationType code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MultilingualJsonType getDescription() {
        return description;
    }

    public ApplicationType description(MultilingualJsonType description) {
        this.description = description;
        return this;
    }

    public void setDescription(MultilingualJsonType description) {
        this.description = description;
    }

    public MultilingualJsonType getSummary() {
        return summary;
    }

    public ApplicationType summary(MultilingualJsonType summary) {
        this.summary = summary;
        return this;
    }

    public void setSummary(MultilingualJsonType summary) {
        this.summary = summary;
    }

    public ApplicationTypeStatus getStatus() {
        return status;
    }

    public ApplicationType status(ApplicationTypeStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ApplicationTypeStatus status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public ApplicationType sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Set<Service> getServices() {
        return services;
    }

    public ApplicationType services(Set<Service> services) {
        this.services = services;
        return this;
    }

    public ApplicationType addService(Service service) {
        this.services.add(service);
        service.setApplicationType(this);
        return this;
    }

    public ApplicationType removeService(Service service) {
        this.services.remove(service);
        service.setApplicationType(null);
        return this;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
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
        ApplicationType applicationType = (ApplicationType) o;
        if (applicationType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationType{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", description='" + getDescription() + "'" +
            ", summary='" + getSummary() + "'" +
            ", status=" + getStatus() +
            ", sortOrder=" + getSortOrder() +
            "}";
    }
}
