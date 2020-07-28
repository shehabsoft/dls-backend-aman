package ae.rta.dls.backend.service.dto.trf;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the ExamTraining entity.
 */
@ApiModel(description = "Exam Required Training entity. @author Yousef.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExamTrainingViewDTO implements Serializable {

    @JsonIgnore
    private Long id;

    @JsonProperty("tryCode")
    private String tryCode;

    @JsonProperty("classCode")
    private String classCode;

    @JsonProperty("englishTrainingTypeDescription")
    private String englishTrainingTypeDescription;

    @JsonProperty("arabicTrainingTypeDescription")
    private String arabicTrainingTypeDescription;

    @JsonProperty("minRequiredLessonsNo")
    private Integer minRequiredLessonsNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTryCode() {
        return tryCode;
    }

    public void setTryCode(String tryCode) {
        this.tryCode = tryCode;
    }

    public String getEnglishTrainingTypeDescription() {
        return englishTrainingTypeDescription;
    }

    public void setEnglishTrainingTypeDescription(String englishTrainingTypeDescription) {
        this.englishTrainingTypeDescription = englishTrainingTypeDescription;
    }

    public String getArabicTrainingTypeDescription() {
        return arabicTrainingTypeDescription;
    }

    public void setArabicTrainingTypeDescription(String arabicTrainingTypeDescription) {
        this.arabicTrainingTypeDescription = arabicTrainingTypeDescription;
    }

    public Integer getMinRequiredLessonsNo() {
        return minRequiredLessonsNo;
    }

    public void setMinRequiredLessonsNo(Integer minRequiredLessonsNo) {
        this.minRequiredLessonsNo = minRequiredLessonsNo;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
