package ae.rta.dls.backend.service.dto.trn;

import ae.rta.dls.backend.domain.enumeration.prd.LearningFileStatus;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import ae.rta.dls.backend.service.dto.prd.TestDetailDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Learning File Detail DTO
 *
 * @author Mohammad Qasim
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LearningFileDetailDTO extends AbstractAuditingDTO {

    @JsonProperty("licenseCategoryCode")
    private String licenseCategoryCode;

    @JsonProperty("tradeLicenseNo")
    private String tradeLicenseNo;

    @JsonProperty("corporateArabicName")
    private String corporateArabicName;

    @JsonProperty("corporateEnglishName")
    private String corporateEnglishName;

    @JsonProperty("status")
    private LearningFileStatus status;

    @JsonProperty("statusDate")
    private LocalDateTime statusDate;

    @JsonProperty("withMaleInstructorPermit")
    private Boolean withMaleInstructorPermit;

    @JsonProperty("registrationDate")
    private LocalDateTime registrationDate;

    @JsonProperty("lastTestDate")
    private LocalDateTime lastTestDate;

    @JsonProperty("lastRenewalDate")
    private LocalDateTime lastRenewalDate;

    @JsonProperty("permitExpiryDate")
    private LocalDate permitExpiryDate;

    @JsonProperty("learningFileExpiryDate")
    private LocalDate learningFileExpiryDate;

    @JsonProperty("roadTest")
    private TestDetailDTO roadTest;

    @JsonProperty("yardTest")
    private TestDetailDTO yardTest;

    @JsonProperty("theoryTest")
    private TestDetailDTO theoryTest;

    @JsonProperty("nocAttachment")
    private String nocAttachment;

    @JsonProperty("nocAttachmentMimeType")
    private String nocAttachmentMimeType;

    public String getLicenseCategoryCode() {
        return licenseCategoryCode;
    }

    public void setLicenseCategoryCode(String licenseCategoryCode) {
        this.licenseCategoryCode = licenseCategoryCode;
    }

    public String getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public void setTradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
    }

    public String getCorporateArabicName() {
        return corporateArabicName;
    }

    public void setCorporateArabicName(String corporateArabicName) {
        this.corporateArabicName = corporateArabicName;
    }

    public String getCorporateEnglishName() {
        return corporateEnglishName;
    }

    public void setCorporateEnglishName(String corporateEnglishName) {
        this.corporateEnglishName = corporateEnglishName;
    }

    public LearningFileStatus getStatus() {
        return status;
    }

    public void setStatus(LearningFileStatus status) {
        this.status = status;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    public Boolean getWithMaleInstructorPermit() {
        return withMaleInstructorPermit;
    }

    public void setWithMaleInstructorPermit(Boolean withMaleInstructorPermit) {
        this.withMaleInstructorPermit = withMaleInstructorPermit;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getLastTestDate() {
        return lastTestDate;
    }

    public void setLastTestDate(LocalDateTime lastTestDate) {
        this.lastTestDate = lastTestDate;
    }

    public LocalDateTime getLastRenewalDate() {
        return lastRenewalDate;
    }

    public void setLastRenewalDate(LocalDateTime lastRenewalDate) {
        this.lastRenewalDate = lastRenewalDate;
    }

    public LocalDate getPermitExpiryDate() {
        return permitExpiryDate;
    }

    public void setPermitExpiryDate(LocalDate permitExpiryDate) {
        this.permitExpiryDate = permitExpiryDate;
    }

    public LocalDate getLearningFileExpiryDate() {
        return learningFileExpiryDate;
    }

    public void setLearningFileExpiryDate(LocalDate learningFileExpiryDate) {
        this.learningFileExpiryDate = learningFileExpiryDate;
    }

    public String getNocAttachment() {
        return nocAttachment;
    }

    public void setNocAttachment(String nocAttachment) {
        this.nocAttachment = nocAttachment;
    }

    public String getNocAttachmentMimeType() {
        return nocAttachmentMimeType;
    }

    public void setNocAttachmentMimeType(String nocAttachmentMimeType) {
        this.nocAttachmentMimeType = nocAttachmentMimeType;
    }

    public TestDetailDTO getRoadTest() {
        return roadTest;
    }

    public void setRoadTest(TestDetailDTO roadTest) {
        this.roadTest = roadTest;
    }

    public TestDetailDTO getYardTest() {
        return yardTest;
    }

    public void setYardTest(TestDetailDTO yardTest) {
        this.yardTest = yardTest;
    }

    public TestDetailDTO getTheoryTest() {
        return theoryTest;
    }

    public void setTheoryTest(TestDetailDTO theoryTest) {
        this.theoryTest = theoryTest;
    }


    @Override
    public String toString() {
        return "LearningFileDetailDTO{" +
            "licenseCategoryCode='" + licenseCategoryCode + '\'' +
            ", tradeLicenseNo='" + tradeLicenseNo + '\'' +
            ", corporateArabicName='" + corporateArabicName + '\'' +
            ", corporateEnglishName='" + corporateEnglishName + '\'' +
            ", status=" + status +
            ", statusDate=" + statusDate +
            ", withMaleInstructorPermit=" + withMaleInstructorPermit +
            ", registrationDate=" + registrationDate +
            ", lastTestDate=" + lastTestDate +
            ", lastRenewalDate=" + lastRenewalDate +
            ", permitExpiryDate=" + permitExpiryDate +
            ", learningFileExpiryDate=" + learningFileExpiryDate +
            ", roadTest=" + roadTest +
            ", yardTest=" + yardTest +
            ", theoryTest=" + theoryTest +
            ", nocAttachment='" + nocAttachment + '\'' +
            ", nocAttachmentMimeType='" + nocAttachmentMimeType + '\'' +
            '}';
    }
}
