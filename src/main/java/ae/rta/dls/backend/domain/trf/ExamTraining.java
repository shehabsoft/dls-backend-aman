package ae.rta.dls.backend.domain.trf;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Exam Required Training entity.
 * @author Yousef.
 */
@Entity
@Table(name = "EXAM_TRAINING_VIEW")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExamTraining implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "TRY_CODE")
    private String tryCode;

    @Column(name = "CLASS_CODE")
    private String classCode;

    @Column(name = "TRAINING_TYPE_DESC_E")
    private String englishTrainingTypeDescription;

    @Column(name = "TRAINING_TYPE_DESC_A")
    private String arabicTrainingTypeDescription;

    @Column(name = "MIN_REQUIRED_LESSON_NO")
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

    public ExamTraining tryCode(String tryCode) {
        this.tryCode = tryCode;
        return this;
    }

    public void setTryCode(String tryCode) {
        this.tryCode = tryCode;
    }

    public String getEnglishTrainingTypeDescription() {
        return englishTrainingTypeDescription;
    }

    public ExamTraining englishTrainingTypeDescription(String englishTrainingTypeDescription) {
        this.englishTrainingTypeDescription = englishTrainingTypeDescription;
        return this;
    }

    public void setEnglishTrainingTypeDescription(String englishTrainingTypeDescription) {
        this.englishTrainingTypeDescription = englishTrainingTypeDescription;
    }

    public String getArabicTrainingTypeDescription() {
        return arabicTrainingTypeDescription;
    }

    public ExamTraining arabicTrainingTypeDescription(String arabicTrainingTypeDescription) {
        this.arabicTrainingTypeDescription = arabicTrainingTypeDescription;
        return this;
    }

    public void setArabicTrainingTypeDescription(String arabicTrainingTypeDescription) {
        this.arabicTrainingTypeDescription = arabicTrainingTypeDescription;
    }

    public Integer getMinRequiredLessonsNo() {
        return minRequiredLessonsNo;
    }

    public ExamTraining minRequiredLessonsNo(Integer minRequiredLessonsNo) {
        this.minRequiredLessonsNo = minRequiredLessonsNo;
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExamTraining examTraining = (ExamTraining) o;

        if (examTraining.getTryCode() == null || getTryCode() == null || examTraining.getClassCode() == null || getClassCode() == null) {
            return false;
        }
        return Objects.equals(getTryCode(), examTraining.getTryCode()) && Objects.equals(getClassCode(), examTraining.getClassCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamTraining{" +
            "id=" + getId() +
            ", tryCode=" + getTryCode() +
            ", classCode=" + getClassCode() +
            ", englishTrainingTypeDescription='" + getEnglishTrainingTypeDescription() + "'" +
            ", arabicTrainingTypeDescription='" + getArabicTrainingTypeDescription() + "'" +
            ", totalLessons=" + getMinRequiredLessonsNo() +
            "}";
    }
}
