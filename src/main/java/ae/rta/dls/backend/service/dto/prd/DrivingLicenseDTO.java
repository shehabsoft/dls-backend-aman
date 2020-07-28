package ae.rta.dls.backend.service.dto.prd;
import ae.rta.dls.backend.domain.type.DrivingLicenseProductJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the DrivingLicense entity.
 */
@ApiModel(description = "DrivingLicense (prd_driving_license) entity. @author Mena Emiel.")
public class DrivingLicenseDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    @JsonProperty("serviceRequestReferenceNo")
    private Long serviceRequestId;

    @NotNull
    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @Lob
    @JsonProperty("productDocument")
    private DrivingLicenseProductJsonType productDocument;

    private String technicalRemarks;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public DrivingLicenseProductJsonType getProductDocument() {
        return productDocument;
    }

    public void setProductDocument(DrivingLicenseProductJsonType productDocument) {
        this.productDocument = productDocument;
    }

    public String getTechnicalRemarks() {
        return technicalRemarks;
    }

    public void setTechnicalRemarks(String technicalRemarks) {
        this.technicalRemarks = technicalRemarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DrivingLicenseDTO drivingLicenseDTO = (DrivingLicenseDTO) o;
        if (drivingLicenseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drivingLicenseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DrivingLicenseDTO{" +
            "id=" + getId() +
            ", serviceRequestId=" + getServiceRequestId() +
            ", applicationId=" + getApplicationId() +
            ", productDocument='" + getProductDocument() + "'" +
            ", technicalRemarks='" + getTechnicalRemarks() + "'" +
            "}";
    }
}
