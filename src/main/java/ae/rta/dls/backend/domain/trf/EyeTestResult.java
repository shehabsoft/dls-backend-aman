package ae.rta.dls.backend.domain.trf;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Eye Test Result entity.
 * @author Mohammad Qasim
 */
@Entity
@Table(name = "EYE_TEST_RESULT_VIEW")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EyeTestResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "RIGHT_EYE_RESULT")
    private Integer rightEyeResult;

    @Column(name = "LEFT_EYE_RESULT")
    private Integer leftEyeResult;

    @Column(name = "WITH_LENSES")
    private Integer withLicense;

    @Column(name = "TEST_DATE")
    private LocalDate testDate;

    @Column(name = "OPTICIAN_DESCRIPTION_A")
    private String arabicOpticianDescription;

    @Column(name = "OPTICIAN_DESCRIPTION_E")
    private String englishOpticianDescription;

    @Column(name = "EID_NUMBER")
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
        return "EyeTestResult {" +
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
