package ae.rta.dls.backend.service.dto.trn;

import ae.rta.dls.backend.domain.enumeration.prd.DrivingLicenseStatus;
import ae.rta.dls.backend.domain.type.ForeignLicenseCategoryDetailsJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import ae.rta.dls.backend.domain.type.CountryJsonType;
import ae.rta.dls.backend.service.dto.prd.NationalityDTO;
import ae.rta.dls.backend.service.dto.sys.DomainValueDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Foreign Driving License Detail DTO/
 *
 * @author Yousef Abu Amireh
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ForeignLicenseDetailDTO extends AbstractAuditingDTO {

    @JsonProperty("licenseNo")
    private String licenseNo;

    @JsonProperty("templateId")
    private Long templateId;

    @JsonProperty("validity")
    private DomainValueDTO validity;

    @JsonProperty("experience")
    private DomainValueDTO experience;

    @JsonProperty("issueDate")
    private LocalDate issueDate;

    @JsonProperty("expiryDate")
    private LocalDate expiryDate;

    @JsonProperty("status")
    private DrivingLicenseStatus status;

    @JsonProperty("statusDate")
    private LocalDateTime statusDate;

    @JsonProperty("replacedInCountry")
    private CountryJsonType replacedInCountry;

    @JsonProperty("issuedFromCountryDetails")
    private CountryJsonType issuedFromCountryDetails;

    @JsonProperty("categoryDetails")
    private List<ForeignLicenseCategoryDetailsJsonType> categoryDetails;

    @JsonProperty("driverNationality")
    private NationalityDTO driverNationality;

    @JsonProperty("frontSideImage")
    private String frontSideImage;

    @JsonProperty("backSideImage")
    private String backSideImage;

    @JsonProperty("translationDocument")
    private String translationDocument;

    public DrivingLicenseStatus getStatus() {
        return status;
    }

    public void setStatus(DrivingLicenseStatus status) {
        this.status = status;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

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

    public CountryJsonType getReplacedInCountry() {
        return replacedInCountry;
    }

    public void setReplacedInCountry(CountryJsonType replacedInCountry) {
        this.replacedInCountry = replacedInCountry;
    }

    public CountryJsonType getIssuedFromCountryDetails() {
        return issuedFromCountryDetails;
    }

    public void setIssuedFromCountryDetails(CountryJsonType issuedFromCountryDetails) {
        this.issuedFromCountryDetails = issuedFromCountryDetails;
    }

    public List<ForeignLicenseCategoryDetailsJsonType> getCategoryDetails() {
        return categoryDetails;
    }

    public void setCategoryDetails(List<ForeignLicenseCategoryDetailsJsonType> categoryDetails) {
        this.categoryDetails = categoryDetails;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public DomainValueDTO getValidity() {
        return validity;
    }

    public void setValidity(DomainValueDTO validity) {
        this.validity = validity;
    }

    public DomainValueDTO getExperience() {
        return experience;
    }

    public void setExperience(DomainValueDTO experience) {
        this.experience = experience;
    }

    public NationalityDTO getDriverNationality() {
        return driverNationality;
    }

    public void setDriverNationality(NationalityDTO driverNationality) {
        this.driverNationality = driverNationality;
    }

    public String getFrontSideImage() {
        return frontSideImage;
    }

    public void setFrontSideImage(String frontSideImage) {
        this.frontSideImage = frontSideImage;
    }

    public String getBackSideImage() {
        return backSideImage;
    }

    public void setBackSideImage(String backSideImage) {
        this.backSideImage = backSideImage;
    }

    public String getTranslationDocument() {
        return translationDocument;
    }

    public void setTranslationDocument(String translationDocument) {
        this.translationDocument = translationDocument;
    }

    @Override
    public String toString() {
        return "ForeignLicenseDetailDTO{" +
            ", licenseNo=" + getLicenseNo() +
            ", issueDate=" + getIssueDate() +
            ", expiryDate=" + getExpiryDate() +
            ", replacedInCountry=" + getReplacedInCountry() +
            ", issuedFromCountryDetails=" + getIssuedFromCountryDetails() +
            ", categoryDetails=" + getCategoryDetails() +
            ", status=" + getStatus() +
            ", statusDate=" + getStatusDate() +
            ", validity=" + getValidity() +
            ", experience=" + getExperience() +
            '}';
    }
}
