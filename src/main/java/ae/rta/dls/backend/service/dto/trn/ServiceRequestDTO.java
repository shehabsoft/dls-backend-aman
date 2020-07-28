package ae.rta.dls.backend.service.dto.trn;
import ae.rta.dls.backend.domain.type.FeeDocumentJsonType;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.domain.type.ServiceDocumentJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import ae.rta.dls.backend.service.dto.sct.ServiceDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;
import ae.rta.dls.backend.domain.enumeration.trn.ServiceRequestStatus;

/**
 * A DTO for the ServiceRequest entity.
 */
@ApiModel(description = "ServiceRequest (trn_service_request) entity. @author Mena Emiel.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceRequestDTO extends AbstractAuditingDTO implements Serializable {

    @JsonProperty("serviceReferenceNo")
    private Long id;

    @NotNull
    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @JsonProperty("applicationDetails")
    private ApplicationDTO applicationDetails;

    @NotNull
    @JsonProperty("serviceCode")
    private String serviceCode;

    @JsonProperty("phaseType")
    private PhaseType phaseType;

    @JsonProperty("status")
    private ServiceRequestStatus status;

    @JsonProperty("statusDate")
    private LocalDateTime statusDate;

    @JsonProperty("statusDescription")
    private MultilingualJsonType statusDescription;

    @NotNull
    @Lob
    @JsonProperty("serviceDocument")
    private ServiceDocumentJsonType serviceDocument;

    @JsonProperty("totalFeeAmount")
    private Double totalFeeAmount;

    @JsonProperty("paidBy")
    private String paidBy;

    @JsonProperty("paymentMethod")
    private String paymentMethod;

    @JsonProperty("paymentReference")
    private Long paymentReference;

    @JsonProperty("paymentDate")
    private LocalDateTime paymentDate;

    @JsonProperty("rejectedBy")
    private String rejectedBy;

    @JsonProperty("rejectionReason")
    private String rejectionReason;

    @JsonProperty("rejectionDate")
    private LocalDateTime rejectionDate;

    @JsonProperty("processInstanceId")
    private Long processInstanceId;

    private Long reversedById;

    @Lob
    @JsonProperty("feeDocument")
    private FeeDocumentJsonType feeDocument;

    @JsonProperty("serviceDetails")
    private ServiceDTO serviceDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public ServiceDocumentJsonType getServiceDocument() {
        return serviceDocument;
    }

    public void setServiceDocument(ServiceDocumentJsonType serviceDocument) {
        this.serviceDocument = serviceDocument;
    }

    public PhaseType getPhaseType() {
        return phaseType;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public ServiceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceRequestStatus status) {
        this.status = status;
    }

    public MultilingualJsonType getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(MultilingualJsonType statusDescription) {
        this.statusDescription = statusDescription;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    public Double getTotalFeeAmount() {
        return totalFeeAmount;
    }

    public void setTotalFeeAmount(Double totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(LocalDateTime rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getReversedById() {
        return reversedById;
    }

    public void setReversedById(Long serviceRequestId) {
        this.reversedById = serviceRequestId;
    }

    public ApplicationDTO getApplicationDetails() {
        return applicationDetails;
    }

    public void setApplicationDetails(ApplicationDTO applicationDetails) {
        this.applicationDetails = applicationDetails;
    }

    public FeeDocumentJsonType getFeeDocument() {
        return feeDocument;
    }

    public void setFeeDocument(FeeDocumentJsonType feeDocument) {
        this.feeDocument = feeDocument;
    }

    public ServiceDTO getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(ServiceDTO serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceRequestDTO serviceRequestDTO = (ServiceRequestDTO) o;
        if (serviceRequestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRequestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


    @Override
    public String toString() {
        return "ServiceRequestDTO{" +
            "id=" + id +
            ", applicationId=" + applicationId +
            ", applicationDetails=" + applicationDetails +
            ", serviceCode='" + serviceCode + '\'' +
            ", phaseType=" + phaseType +
            ", status=" + status +
            ", statusDate=" + statusDate +
            ", statusDescription=" + statusDescription +
            ", serviceDocument=" + serviceDocument +
            ", totalFeeAmount=" + totalFeeAmount +
            ", paidBy='" + paidBy + '\'' +
            ", paymentMethod='" + paymentMethod + '\'' +
            ", paymentReference=" + paymentReference +
            ", paymentDate=" + paymentDate +
            ", rejectedBy='" + rejectedBy + '\'' +
            ", rejectionReason='" + rejectionReason + '\'' +
            ", rejectionDate=" + rejectionDate +
            ", processInstanceId=" + processInstanceId +
            ", reversedById=" + reversedById +
            ", feeDocument=" + feeDocument +
            ", serviceDetails=" + serviceDetails +
            '}';
    }
}
