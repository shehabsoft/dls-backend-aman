package ae.rta.dls.backend.domain.trn;


import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import ae.rta.dls.backend.domain.enumeration.trn.ViolationLevel;

/**
 * ApplicationViolation (trn_application_violation) entity.
 * @author Mena Emiel.
 */
@Entity
@Table(name = "trn_application_violation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationViolation extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "apviSequenceGenerator")
    @SequenceGenerator(name = "apviSequenceGenerator", sequenceName = "apvi_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "is_eligible_for_exemption", nullable = false)
    private Boolean isEligibleForExemption;

    @NotNull
    @Column(name = "is_exempted", nullable = false)
    private Boolean isExempted;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "violation_level", nullable = false)
    private ViolationLevel violationLevel;

    @Column(name = "exemption_process_id")
    private Long exemptionProcessId;

    @Column(name = "exempted_by")
    private String exemptedBy;

    @Column(name = "exemption_date")
    private LocalDateTime exemptionDate;

    @ManyToOne
    @JsonIgnoreProperties("applicationViolations")
    private Application application;

    @ManyToOne
    @JsonIgnoreProperties("applicationViolations")
    private ServiceRequest serviceRequest;

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

    public ApplicationViolation code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isIsEligibleForExemption() {
        return isEligibleForExemption;
    }

    public ApplicationViolation isEligibleForExemption(Boolean isEligibleForExemption) {
        this.isEligibleForExemption = isEligibleForExemption;
        return this;
    }

    public void setIsEligibleForExemption(Boolean isEligibleForExemption) {
        this.isEligibleForExemption = isEligibleForExemption;
    }

    public Boolean isIsExempted() {
        return isExempted;
    }

    public ApplicationViolation isExempted(Boolean isExempted) {
        this.isExempted = isExempted;
        return this;
    }

    public void setIsExempted(Boolean isExempted) {
        this.isExempted = isExempted;
    }

    public ViolationLevel getViolationLevel() {
        return violationLevel;
    }

    public ApplicationViolation violationlevel(ViolationLevel violationLevel) {
        this.violationLevel = violationLevel;
        return this;
    }

    public void setViolationLevel(ViolationLevel violationLevel) {
        this.violationLevel = violationLevel;
    }

    public Long getExemptionProcessId() {
        return exemptionProcessId;
    }

    public ApplicationViolation exemptionProcessId(Long exemptionProcessId) {
        this.exemptionProcessId = exemptionProcessId;
        return this;
    }

    public void setExemptionProcessId(Long exemptionProcessId) {
        this.exemptionProcessId = exemptionProcessId;
    }

    public String getExemptedBy() {
        return exemptedBy;
    }

    public ApplicationViolation exemptedBy(String exemptedBy) {
        this.exemptedBy = exemptedBy;
        return this;
    }

    public void setExemptedBy(String exemptedBy) {
        this.exemptedBy = exemptedBy;
    }

    public LocalDateTime getExemptionDate() {
        return exemptionDate;
    }

    public ApplicationViolation exemptionDate(LocalDateTime exemptionDate) {
        this.exemptionDate = exemptionDate;
        return this;
    }

    public void setExemptionDate(LocalDateTime exemptionDate) {
        this.exemptionDate = exemptionDate;
    }

    public Application getApplication() {
        return application;
    }

    public ApplicationViolation application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public ApplicationViolation serviceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
        return this;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
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
        ApplicationViolation applicationViolation = (ApplicationViolation) o;
        if (applicationViolation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationViolation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationViolation{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", isEligibleForExemption='" + isIsEligibleForExemption() + "'" +
            ", isExempted='" + isIsExempted() + "'" +
            ", violationLevel='" + getViolationLevel() + "'" +
            ", exemptionProcessId=" + getExemptionProcessId() +
            ", exemptedBy='" + getExemptedBy() + "'" +
            ", exemptionDate='" + getExemptionDate() + "'" +
            "}";
    }
}
