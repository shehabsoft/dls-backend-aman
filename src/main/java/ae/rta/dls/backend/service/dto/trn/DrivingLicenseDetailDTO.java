package ae.rta.dls.backend.service.dto.trn;

import ae.rta.dls.backend.domain.type.DrivingLicenseCategoryDetailJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 *  Driving License Detail DTO
 *
 * @author Mohammad Qasim
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DrivingLicenseDetailDTO extends AbstractAuditingDTO {

    @JsonProperty("licenseNo")
    private String licenseNo;

    @JsonProperty("issueDate")
    private LocalDate issueDate;

    @JsonProperty("expiryDate")
    private LocalDate expiryDate;

    @JsonProperty("categoryDetails")
    private DrivingLicenseCategoryDetailJsonType categoryDetails;

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public DrivingLicenseCategoryDetailJsonType getCategoryDetails() {
        return categoryDetails;
    }

    public void setCategoryDetails(DrivingLicenseCategoryDetailJsonType categoryDetails) {
        this.categoryDetails = categoryDetails;
    }

    @Override
    public String toString() {
        return "DrivingLicenseDetailDTO{" +
            ", licenseNo=" + getLicenseNo() +
            ", issueDate=" + getIssueDate() +
            ", expiryDate=" + getExpiryDate() +
            ", categoryDetails=" + getCategoryDetails() +
            '}';
    }
}
