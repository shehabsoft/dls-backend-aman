package ae.rta.dls.backend.service.dto.trn;

import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.type.ApplicationCriteriaJsonType;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ae.rta.dls.backend.domain.enumeration.trn.ApplicationStatus;
import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;


/**
 * A DTO for the Application entity.
 */
@ApiModel(description = "Application (trn_application) entity. @author Mena Emiel.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationDTO extends AbstractAuditingDTO implements Serializable {

    @JsonProperty("applicationReferenceNo")
    private Long id;

    @JsonProperty("applicationTypeId")
    private Long applicationTypeId;

    @JsonProperty("applicationCriteria")
    private ApplicationCriteriaJsonType applicationCriteria;

    @JsonProperty("channelCode")
    private String channelCode;

    @JsonProperty("status")
    private ApplicationStatus status;

    @JsonProperty("statusDescription")
    private MultilingualJsonType statusDescription;

    @JsonProperty("statusDate")
    private LocalDateTime statusDate;

    @JsonProperty("activePhase")
    private PhaseType activePhase;

    @JsonProperty("activePhaseDescription")
    private MultilingualJsonType activePhaseDescription;

    @JsonProperty("confirmedBy")
    private String confirmedBy;

    @JsonProperty("confirmationDate")
    private LocalDateTime confirmationDate;

    @JsonProperty("rejectedBy")
    private String rejectedBy;

    @JsonProperty("rejectionReason")
    private String rejectionReason;

    @JsonProperty("rejectionDate")
    private LocalDateTime rejectionDate;

    @JsonProperty("processInstanceId")
    private Long processInstanceId;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("englishCustomerName")
    private String englishCustomerName;

    @JsonProperty("arabicCustomerName")
    private String arabicCustomerName;

    @JsonProperty("tradeLicenseNo")
    private String tradeLicenseNo;

    @JsonProperty("englishCorporateName")
    private String englishCorporateName;

    @JsonProperty("arabicCorporateName")
    private String arabicCorporateName;

    @JsonProperty("userType")
    private String userType;

    @JsonProperty("userTypeDescription")
    private MultilingualJsonType userTypeDescription;

    @JsonProperty("userRole")
    private String userRole;

    @JsonProperty("persona")
    private String persona;

    @JsonProperty("serviceRequests")
    private Set<ServiceRequestDTO> serviceRequests = new HashSet<>();

    @JsonProperty("personaVersion")
    private String personaVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
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

    public PhaseType getActivePhase() {
        return activePhase;
    }

    public void setActivePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
    }

    public MultilingualJsonType getActivePhaseDescription() {
        return activePhaseDescription;
    }

    public void setActivePhaseDescription(MultilingualJsonType activePhaseDescription) {
        this.activePhaseDescription = activePhaseDescription;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public LocalDateTime getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(LocalDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
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

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public void setTradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public MultilingualJsonType getUserTypeDescription() {
        return userTypeDescription;
    }

    public void setUserTypeDescription(MultilingualJsonType userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public ApplicationCriteriaJsonType getApplicationCriteria() {
        return applicationCriteria;
    }

    public void setApplicationCriteria(ApplicationCriteriaJsonType applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
    }

    public Long getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(Long applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public Set<ServiceRequestDTO> getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(Set<ServiceRequestDTO> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public String getEnglishCustomerName() {
        return StringUtil.formatString(englishCustomerName);
    }

    public void setEnglishCustomerName(String englishCustomerName) {
        this.englishCustomerName = englishCustomerName;
    }

    public String getArabicCustomerName() {
        return arabicCustomerName;
    }

    public void setArabicCustomerName(String arabicCustomerName) {
        this.arabicCustomerName = arabicCustomerName;
    }

    public String getEnglishCorporateName() {
        return englishCorporateName;
    }

    public void setEnglishCorporateName(String englishCorporateName) {
        this.englishCorporateName = englishCorporateName;
    }

    public String getArabicCorporateName() {
        return arabicCorporateName;
    }

    public void setArabicCorporateName(String arabicCorporateName) {
        this.arabicCorporateName = arabicCorporateName;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getPersonaVersion() {
        return personaVersion;
    }

    public void setPersonaVersion(String personaVersion) {
        this.personaVersion = personaVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationDTO applicationDTO = (ApplicationDTO) o;
        if (applicationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", statusDescription='" + getStatusDescription() + "'" +
            ", statusDate='" + getStatusDate() + "'" +
            ", activePhase='" + getActivePhase() + "'" +
            ", activePhaseDescription='" + getActivePhaseDescription() + "'" +
            ", confirmedBy='" + getConfirmedBy() + "'" +
            ", confirmationDate='" + getConfirmationDate() + "'" +
            ", rejectedBy='" + getRejectedBy() + "'" +
            ", rejectionReason='" + getRejectionReason() + "'" +
            ", rejectionDate='" + getRejectionDate() + "'" +
            ", processInstanceId=" + getProcessInstanceId() +
            ", channelCode='" + getChannelCode() + "'" +
            ", userId='" + getUserId() + "'" +
            ", englishCustomerName='" + getEnglishCustomerName() + "'" +
            ", arabicCustomerName='" + getArabicCustomerName() + "'" +
            ", tradeLicenseNo='" + getTradeLicenseNo() + "'" +
            ", englishCorporateName='" + getEnglishCorporateName() + "'" +
            ", arabicCorporateName='" + getArabicCorporateName() + "'" +
            ", userType='" + getUserType() + "'" +
            ", userTypeDescription='" + getUserTypeDescription() + "'" +
            ", userRole='" + getUserRole() + "'" +
            ", applicationCriteria=" + getApplicationCriteria() +
            ", applicationType=" + getApplicationTypeId() +
            ", persona='" + getPersona() + "'" +
            ", personaVersion='" + getPersonaVersion() + "'" +
            "}";
    }
}
