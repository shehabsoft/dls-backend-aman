package ae.rta.dls.backend.service.dto.trn;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import ae.rta.dls.backend.domain.enumeration.trn.ViolationLevel;

/**
 * A DTO for the ApplicationViolation entity.
 */
@ApiModel(description = "ApplicationViolation (trn_application_violation) entity. @author Mena Emiel.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationViolationDTO extends AbstractAuditingDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("code")
    private String code;

    @NotNull
    @JsonProperty("isEligibleForExemption")
    private Boolean isEligibleForExemption;

    @NotNull
    @JsonProperty("isExempted")
    private Boolean isExempted;

    @NotNull
    @JsonProperty("violationLevel")
    private ViolationLevel violationLevel;

    @JsonProperty("exemptionProcessId")
    private Long exemptionProcessId;

    @JsonProperty("exemptedBy")
    private String exemptedBy;

    @JsonProperty("exemptionDate")
    private LocalDateTime exemptionDate;

    private Long applicationId;

    private Long serviceRequestId;

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

    public Boolean isIsEligibleForExemption() {
        return isEligibleForExemption;
    }

    public void setIsEligibleForExemption(Boolean isEligibleForExemption) {
        this.isEligibleForExemption = isEligibleForExemption;
    }

    public Boolean isIsExempted() {
        return isExempted;
    }

    public void setIsExempted(Boolean isExempted) {
        this.isExempted = isExempted;
    }

    public ViolationLevel getViolationLevel() {
        return violationLevel;
    }

    public void setViolationLevel(ViolationLevel violationLevel) {
        this.violationLevel = violationLevel;
    }

    public Long getExemptionProcessId() {
        return exemptionProcessId;
    }

    public void setExemptionProcessId(Long exemptionProcessId) {
        this.exemptionProcessId = exemptionProcessId;
    }

    public String getExemptedBy() {
        return exemptedBy;
    }

    public void setExemptedBy(String exemptedBy) {
        this.exemptedBy = exemptedBy;
    }

    public LocalDateTime getExemptionDate() {
        return exemptionDate;
    }

    public void setExemptionDate(LocalDateTime exemptionDate) {
        this.exemptionDate = exemptionDate;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationViolationDTO applicationViolationDTO = (ApplicationViolationDTO) o;
        if (applicationViolationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationViolationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationViolationDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", isEligibleForExemption='" + isIsEligibleForExemption() + "'" +
            ", isExempted='" + isIsExempted() + "'" +
            ", violationLevel='" + getViolationLevel() + "'" +
            ", exemptionProcessId=" + getExemptionProcessId() +
            ", exemptedBy='" + getExemptedBy() + "'" +
            ", exemptionDate='" + getExemptionDate() + "'" +
            ", application=" + getApplicationId() +
            ", serviceRequest=" + getServiceRequestId() +
            "}";
    }
}
