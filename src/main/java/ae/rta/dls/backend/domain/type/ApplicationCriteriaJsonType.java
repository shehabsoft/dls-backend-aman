package ae.rta.dls.backend.domain.type;

import ae.rta.dls.backend.domain.enumeration.common.Language;
import ae.rta.dls.backend.domain.enumeration.trn.*;
import ae.rta.dls.backend.service.dto.prd.TestDetailDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Application Criteria Json Type.
 *
 * This type was developed to application criteria generic attributes to be stored on the application level.
 *
 * @author Mena Emiel
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationCriteriaJsonType implements Serializable {

    @JsonProperty("isExistingCustomer")
    private Boolean existingCustomer;

    @JsonProperty("mobileNo")
    private String mobileNo;

    @JsonProperty("birthdate")
    private LocalDate birthdate;

    @JsonProperty("eidNumber")
    private String eidNumber;

    @JsonProperty("eidExpiryDate")
    private LocalDate eidExpiryDate;

    @JsonProperty("nationalityCode")
    private String nationalityCode;

    @JsonProperty("nationalityDescription")
    private MultilingualJsonType nationalityDescription;

    @JsonProperty("passportNo")
    private String passportNo;

    @JsonProperty("passportExpiryDate")
    private LocalDate passportExpiryDate;

    @JsonProperty("hasForeignLicense")
    private Boolean hasForeignLicense;

    @JsonProperty("eyeTest")
    private EyeTestResult eyeTest;

    @JsonProperty("theoryLecture")
    private TheoryLectureJsonType theoryLecture;

    @JsonProperty("drivingLessons")
    private DrivingLessonsJsonType drivingLessons;

    @JsonProperty("averageQuotationPrice")
    private Integer averageQuotationPrice;

    @JsonProperty("needsVipTrainingCourse")
    private Boolean needsVipTrainingCourse;

    @JsonProperty("nocType")
    private NocType nocType;

    @JsonProperty("corporate")
    private CorporateJsonType corporate;

    @JsonProperty("rtaReviewStatus")
    private RtaReviewStatus rtaReviewStatus;

    @JsonProperty("drivingSchoolReviewStatus")
    private DrivingSchoolReviewStatus drivingSchoolReviewStatus;

    @JsonProperty("clearanceStatus")
    private ClearanceStatus clearanceStatus;

    @JsonProperty("licenseCategoryCode")
    private String licenseCategoryCode;

    @JsonProperty("roadTest")
    private TestDetailDTO roadTest;

    @JsonProperty("yardTest")
    private TestDetailDTO yardTest;

    @JsonProperty("theoryTest")
    private TestDetailDTO theoryTest;

    @JsonProperty("preferredLanguage")
    private Language preferredLanguage;

    @JsonProperty("tcnFeesCollected")
    private Boolean tcnFeesCollected;

    @JsonProperty("translationDocumentRequired")
    private Boolean translationDocumentRequired;

    @JsonProperty("nationalityChanged")
    private Boolean nationalityChanged;

    @JsonProperty("foreignLicenseReviewed")
    private Boolean foreignLicenseReviewed;

    public Boolean getExistingCustomer() {
        return existingCustomer;
    }

    public void setExistingCustomer(Boolean existingCustomer) {
        this.existingCustomer = existingCustomer;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEidNumber() {
        return eidNumber;
    }

    public void setEidNumber(String eidNumber) {
        this.eidNumber = eidNumber;
    }

    public LocalDate getEidExpiryDate() {
        return eidExpiryDate;
    }

    public void setEidExpiryDate(LocalDate eidExpiryDate) {
        this.eidExpiryDate = eidExpiryDate;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public MultilingualJsonType getNationalityDescription() {
        return nationalityDescription;
    }

    public void setNationalityDescription(MultilingualJsonType nationalityDescription) {
        this.nationalityDescription = nationalityDescription;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public LocalDate getPassportExpiryDate() {
        return passportExpiryDate;
    }

    public void setPassportExpiryDate(LocalDate passportExpiryDate) {
        this.passportExpiryDate = passportExpiryDate;
    }

    public Boolean getHasForeignLicense() {
        return hasForeignLicense;
    }

    public void setHasForeignLicense(Boolean hasForeignLicense) {
        this.hasForeignLicense = hasForeignLicense;
    }

    public EyeTestResult getEyeTest() {
        return eyeTest;
    }

    public void setEyeTest(EyeTestResult eyeTest) {
        this.eyeTest = eyeTest;
    }

    public TheoryLectureJsonType getTheoryLecture() {
        return theoryLecture;
    }

    public void setTheoryLecture(TheoryLectureJsonType theoryLecture) {
        this.theoryLecture = theoryLecture;
    }

    public Integer getAverageQuotationPrice() {
        return averageQuotationPrice;
    }

    public void setAverageQuotationPrice(Integer averageQuotationPrice) {
        this.averageQuotationPrice = averageQuotationPrice;
    }

    public Boolean getNeedsVipTrainingCourse() {
        return needsVipTrainingCourse;
    }

    public void setNeedsVipTrainingCourse(Boolean needsVipTrainingCourse) {
        this.needsVipTrainingCourse = needsVipTrainingCourse;
    }

    public NocType getNocType() {
        return nocType;
    }

    public void setNocType(NocType nocType) {
        this.nocType = nocType;
    }

    public CorporateJsonType getCorporate() {
        return corporate;
    }

    public void setCorporate(CorporateJsonType corporate) {
        this.corporate = corporate;
    }

    public RtaReviewStatus getRtaReviewStatus() {
        return rtaReviewStatus;
    }

    public void setRtaReviewStatus(RtaReviewStatus rtaReviewStatus) {
        this.rtaReviewStatus = rtaReviewStatus;
    }

    public DrivingSchoolReviewStatus getDrivingSchoolReviewStatus() {
        return drivingSchoolReviewStatus;
    }

    public void setDrivingSchoolReviewStatus(DrivingSchoolReviewStatus drivingSchoolReviewStatus) {
        this.drivingSchoolReviewStatus = drivingSchoolReviewStatus;
    }

    public String getLicenseCategoryCode() {
        return licenseCategoryCode;
    }

    public void setLicenseCategoryCode(String licenseCategoryCode) {
        this.licenseCategoryCode = licenseCategoryCode;
    }

    public DrivingLessonsJsonType getDrivingLessons() {
        return drivingLessons;
    }

    public void setDrivingLessons(DrivingLessonsJsonType drivingLessons) {
        this.drivingLessons = drivingLessons;
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

    public ClearanceStatus getClearanceStatus() {
        return clearanceStatus;
    }

    public void setClearanceStatus(ClearanceStatus clearanceStatus) {
        this.clearanceStatus = clearanceStatus;
    }

    public Language getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(Language preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public Boolean getTcnFeesCollected() {
        return tcnFeesCollected;
    }

    public void setTcnFeesCollected(Boolean tcnFeesCollected) {
        this.tcnFeesCollected = tcnFeesCollected;
    }

    public Boolean getTranslationDocumentRequired() {
        return translationDocumentRequired;
    }

    public void setTranslationDocumentRequired(Boolean translationDocumentRequired) {
        this.translationDocumentRequired = translationDocumentRequired;
    }

    public Boolean getNationalityChanged() {
        return nationalityChanged;
    }

    public void setNationalityChanged(Boolean nationalityChanged) {
        this.nationalityChanged = nationalityChanged;
    }

    public Boolean getForeignLicenseReviewed() {
        return foreignLicenseReviewed;
    }

    public void setForeignLicenseReviewed(Boolean foreignLicenseReviewed) {
        this.foreignLicenseReviewed = foreignLicenseReviewed;
    }

    @Override
    public String toString() {
        return "ApplicationCriteriaJsonType{" +
            "existingCustomer=" + existingCustomer +
            ", mobileNo='" + mobileNo + '\'' +
            ", birthdate=" + birthdate +
            ", eidNumber='" + eidNumber + '\'' +
            ", eidExpiryDate=" + eidExpiryDate +
            ", nationalityCode='" + nationalityCode + '\'' +
            ", nationalityDescription=" + nationalityDescription +
            ", passportNo='" + passportNo + '\'' +
            ", passportExpiryDate=" + passportExpiryDate +
            ", hasForeignLicense=" + hasForeignLicense +
            ", eyeTest=" + eyeTest +
            ", theoryLecture=" + theoryLecture +
            ", drivingLessons=" + drivingLessons +
            ", averageQuotationPrice=" + averageQuotationPrice +
            ", needsVipTrainingCourse=" + needsVipTrainingCourse +
            ", nocType=" + nocType +
            ", corporate=" + corporate +
            ", rtaReviewStatus=" + rtaReviewStatus +
            ", drivingSchoolReviewStatus=" + drivingSchoolReviewStatus +
            ", clearanceStatus=" + clearanceStatus +
            ", licenseCategoryCode='" + licenseCategoryCode + '\'' +
            ", roadTest=" + roadTest +
            ", yardTest=" + yardTest +
            ", theoryTest=" + theoryTest +
            ", preferredLanguage=" + preferredLanguage +
            ", tcnFeesCollected=" + tcnFeesCollected +
            '}';
    }
}
