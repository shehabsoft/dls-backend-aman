package ae.rta.dls.backend.service.dto.trf;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the EyeTestResult entity.
 */
@ApiModel(description = "Exam Required Training entity. @author Mohammad Qasim.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EyeTestResultViewDTO implements Serializable {

    @JsonIgnore
    private Long id;

    @JsonProperty("rightEyeResult")
    private Integer rightEyeResult;

    @JsonProperty("leftEyeResult")
    private Integer leftEyeResult;

    @JsonProperty("withLicense")
    private Integer withLicense;

    @JsonProperty("testDate")
    private LocalDate testDate;

    @JsonProperty("arabicOpticianDescription")
    private String arabicOpticianDescription;

    @JsonProperty("englishOpticianDescription")
    private String englishOpticianDescription;

    @JsonProperty("emiratesId")
    private String emiratesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRightEyeResult() {
        return rightEyeResult;
    }

    public void setRightEyeResult(Integer rightEyeResult) {
        this.rightEyeResult = rightEyeResult;
    }

    public Integer getLeftEyeResult() {
        return leftEyeResult;
    }

    public void setLeftEyeResult(Integer leftEyeResult) {
        this.leftEyeResult = leftEyeResult;
    }

    public Integer getWithLicense() {
        return withLicense;
    }

    public void setWithLicense(Integer withLicense) {
        this.withLicense = withLicense;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public String getArabicOpticianDescription() {
        return arabicOpticianDescription;
    }

    public void setArabicOpticianDescription(String arabicOpticianDescription) {
        this.arabicOpticianDescription = arabicOpticianDescription;
    }

    public String getEnglishOpticianDescription() {
        return englishOpticianDescription;
    }

    public void setEnglishOpticianDescription(String englishOpticianDescription) {
        this.englishOpticianDescription = englishOpticianDescription;
    }

    public String getEmiratesId() {
        return emiratesId;
    }

    public void setEmiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
    }

    @Override
    public String toString() {
        return "EyeTestResultDTO {" +
            "id=" + getId() +
            ", rightEyeResult=" + getRightEyeResult() +
            ", leftEyeResult=" + getLeftEyeResult() +
            ", withLicense='" + getWithLicense() + "'" +
            ", testDate='" + getTestDate() + "'" +
            ", arabicOpticianDescription=" + getArabicOpticianDescription() +
            ", englishOpticianDescription=" + getEnglishOpticianDescription() +
            ", emiratesId=" + getEmiratesId() +
            "}";
    }
}
