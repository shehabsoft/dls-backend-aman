package ae.rta.dls.backend.domain.sct;

import ae.rta.dls.backend.domain.AbstractAuditingEntity;

import ae.rta.dls.backend.domain.enumeration.trn.ServiceStatus;
import ae.rta.dls.backend.domain.util.MultilingualJsonConverter;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Service entity.
 * @author Tariq Abu Amireh
 */
@ApiModel(description = "The Service entity. @author Tariq Abu Amireh")
@Entity
@Table(name = "sct_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Service extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servSequenceGenerator")
    @SequenceGenerator(name = "servSequenceGenerator", sequenceName = "serv_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    //@Convert(converter = MultilingualJsonConverter.class)
    @Type(type = "json")
    private MultilingualJsonType name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ServiceStatus status;

    @ManyToOne
    @JsonIgnoreProperties("services")
    private ApplicationType applicationType;

    @ManyToOne
    @JsonIgnoreProperties("services")
    private ServiceGroup serviceGroup;

    @Column(name = "service_impl_class", nullable = false)
    private String serviceImplClass;

    @Column(name = "service_dto_class", nullable = false)
    private String serviceDTOClass;

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

    public Service code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MultilingualJsonType getName() {
        return name;
    }

    public Service name(MultilingualJsonType name) {
        this.name = name;
        return this;
    }

    public void setName(MultilingualJsonType name) {
        this.name = name;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public Service status(ServiceStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public Service applicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
        return this;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public ServiceGroup getServiceGroup() {
        return serviceGroup;
    }

    public Service serviceGroup(ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
        return this;
    }

    public void setServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
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
        Service service = (Service) o;
        if (service.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), service.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Service{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", name='" + getName() + "'" +
            ", status=" + getStatus() +
            ", serviceImplClass=" + getServiceImplClass() +
            ", serviceDTOClass=" + getServiceDTOClass() +
            "}";
    }
}
