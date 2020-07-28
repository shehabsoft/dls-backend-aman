package ae.rta.dls.backend.domain.trn;


import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import ae.rta.dls.backend.domain.type.FeeDocumentJsonType;
import ae.rta.dls.backend.domain.util.FeeDocumentJsonConverter;
import ae.rta.dls.backend.domain.util.MultilingualJsonConverter;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.domain.type.ServiceDocumentJsonType;
import ae.rta.dls.backend.domain.util.ServiceRequestJsonConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;

import ae.rta.dls.backend.domain.enumeration.trn.ServiceRequestStatus;
import org.hibernate.annotations.Cache;

/**
 * ServiceRequest (trn_service_request) entity.
 * @author Mena Emiel.
 */
@Entity
@Table(name = "trn_service_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceRequest extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sereSequenceGenerator")
    @SequenceGenerator(name = "sereSequenceGenerator", sequenceName = "sere_seq", allocationSize = 1)
    private Long id;

    //@Lob
    @Column(name = "service_document")
    @Type(type = "json")
    //@Convert(converter = ServiceRequestJsonConverter.class)
    private ServiceDocumentJsonType serviceDocument;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "phase_type", nullable = false)
    private PhaseType phaseType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ServiceRequestStatus status;

    @Column(name = "status_description", nullable = false)
    @Type(type = "json")
    //@Convert(converter = MultilingualJsonConverter.class)
    private MultilingualJsonType statusDescription;

    @NotNull
    @Column(name = "status_date", nullable = false)
    private LocalDateTime statusDate;

    @Column(name = "total_fee_amount")
    private Double totalFeeAmount;

    @Column(name = "paid_by")
    private String paidBy;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_reference")
    private Long paymentReference;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "rejected_by")
    private String rejectedBy;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "rejection_date")
    private LocalDateTime rejectionDate;

    @Column(name = "process_instance_id")
    private Long processInstanceId;

    @ManyToOne
    @JsonIgnoreProperties("serviceRequests")
    private Application application;

    @NotNull
    @Column(name = "service_code", nullable = false)
    private String serviceCode;

    @OneToMany(mappedBy = "serviceRequest" ,orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationViolation> applicationViolations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("serviceRequests")
    private ServiceRequest reversedBy;

    @Column(name = "synched_entity_id", unique = true)
    private Long synchedEntityId;

    //@Lob
    @Column(name = "fee_document")
    @Type(type = "json")
    //@Convert(converter = FeeDocumentJsonConverter.class)
    private FeeDocumentJsonType feeDocument;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceDocumentJsonType getServiceDocument() {
        return serviceDocument;
    }

    public ServiceRequest serviceDocument(ServiceDocumentJsonType serviceDocument) {
        this.serviceDocument = serviceDocument;
        return this;
    }

    public void setServiceDocument(ServiceDocumentJsonType serviceDocument) {
        this.serviceDocument = serviceDocument;
    }

    public PhaseType getPhaseType() {
        return phaseType;
    }

    public ServiceRequest phaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
        return this;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public ServiceRequestStatus getStatus() {
        return status;
    }

    public ServiceRequest status(ServiceRequestStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ServiceRequestStatus status) {
        this.status = status;
    }

    public MultilingualJsonType getStatusDescription() {
        return statusDescription;
    }

    public ServiceRequest statusDescription(MultilingualJsonType statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }

    public void setStatusDescription(MultilingualJsonType statusDescription) {
        this.statusDescription = statusDescription;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public ServiceRequest statusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    public Double getTotalFeeAmount() {
        return totalFeeAmount;
    }

    public ServiceRequest totalFeeAmount(Double totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
        return this;
    }

    public void setTotalFeeAmount(Double totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public ServiceRequest paidBy(String paidBy) {
        this.paidBy = paidBy;
        return this;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public ServiceRequest paymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPaymentReference() {
        return paymentReference;
    }

    public ServiceRequest paymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
        return this;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public ServiceRequest paymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getRejectedBy() {
        return rejectedBy;
    }

    public ServiceRequest rejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
        return this;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public ServiceRequest rejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
        return this;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getRejectionDate() {
        return rejectionDate;
    }

    public ServiceRequest rejectionDate(LocalDateTime rejectionDate) {
        this.rejectionDate = rejectionDate;
        return this;
    }

    public void setRejectionDate(LocalDateTime rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public ServiceRequest processInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Application getApplication() {
        return application;
    }

    public ServiceRequest application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public ServiceRequest serviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Set<ApplicationViolation> getApplicationViolations() {
        return applicationViolations;
    }

    public ServiceRequest applicationViolations(Set<ApplicationViolation> applicationViolations) {
        this.applicationViolations = applicationViolations;
        return this;
    }

    public ServiceRequest addApplicationViolation(ApplicationViolation applicationViolation) {
        this.applicationViolations.add(applicationViolation);
        applicationViolation.setServiceRequest(this);
        return this;
    }

    public ServiceRequest removeApplicationViolation(ApplicationViolation applicationViolation) {
        this.applicationViolations.remove(applicationViolation);
        applicationViolation.setServiceRequest(null);
        return this;
    }

    public void setApplicationViolations(Set<ApplicationViolation> applicationViolations) {
        this.applicationViolations = applicationViolations;
    }

    public ServiceRequest getReversedBy() {
        return reversedBy;
    }

    public ServiceRequest reversedBy(ServiceRequest serviceRequest) {
        this.reversedBy = serviceRequest;
        return this;
    }

    public void setReversedBy(ServiceRequest serviceRequest) {
        this.reversedBy = serviceRequest;
    }

    public Long getSynchedEntityId() {
        return synchedEntityId;
    }

    public ServiceRequest synchedEntityId(Long synchedEntityId) {
        this.synchedEntityId = synchedEntityId;
        return this;
    }

    public void setSynchedEntityId(Long synchedEntityId) {
        this.synchedEntityId = synchedEntityId;
    }

    public FeeDocumentJsonType getFeeDocument() {
        return feeDocument;
    }

    public ServiceRequest serviceDocument(FeeDocumentJsonType feeDocument) {
        this.feeDocument = feeDocument;
        return this;
    }

    public void setFeeDocument(FeeDocumentJsonType feeDocument) {
        this.feeDocument = feeDocument;
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
        ServiceRequest serviceRequest = (ServiceRequest) o;
        if (serviceRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


    @Override
    public String toString() {
        return "ServiceRequest{" +
            "id=" + id +
            ", serviceDocument=" + serviceDocument +
            ", phaseType=" + phaseType +
            ", status=" + status +
            ", statusDescription=" + statusDescription +
            ", statusDate=" + statusDate +
            ", totalFeeAmount=" + totalFeeAmount +
            ", paidBy='" + paidBy + '\'' +
            ", paymentMethod=" + paymentMethod +
            ", paymentReference=" + paymentReference +
            ", paymentDate=" + paymentDate +
            ", rejectedBy='" + rejectedBy + '\'' +
            ", rejectionReason='" + rejectionReason + '\'' +
            ", rejectionDate=" + rejectionDate +
            ", processInstanceId=" + processInstanceId +
            ", application=" + application +
            ", serviceCode='" + serviceCode + '\'' +
            ", applicationViolations=" + applicationViolations +
            ", reversedBy=" + reversedBy +
            ", synchedEntityId=" + synchedEntityId +
            ", feeDocument=" + feeDocument +
            '}';
    }
}
