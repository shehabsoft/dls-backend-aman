package ae.rta.dls.backend.service.dto.trf;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ForeignLicenseTemplateView entity.
 */
@ApiModel(description = "Held License Template entity. @author Yousef.")
public class ForeignLicenseTemplateViewDTO implements Serializable {

    private Long id;

    private String arabicName;

    private String englishName;

    @Lob
    private String backSideTemplate;

    @Lob
    private String frontSideTemplate;

    private Long countryId;

    private Long stateId;

    private String countryCode;

    private String stateCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getBackSideTemplate() {
        return backSideTemplate;
    }

    public void setBackSideTemplate(String backSideTemplate) {
        this.backSideTemplate = backSideTemplate;
    }

    public String getFrontSideTemplate() {
        return frontSideTemplate;
    }

    public void setFrontSideTemplate(String frontSideTemplate) {
        this.frontSideTemplate = frontSideTemplate;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ForeignLicenseTemplateViewDTO foreignLicenseTemplateViewDTO = (ForeignLicenseTemplateViewDTO) o;
        if (foreignLicenseTemplateViewDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foreignLicenseTemplateViewDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HeldLicenseTemplateDTO{" +
            "id=" + getId() +
            ", arabicName='" + getArabicName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            ", backSideTemplate='" + getBackSideTemplate() + "'" +
            ", frontSideTemplate='" + getFrontSideTemplate() + "'" +
            ", countryId=" + getCountryId() +
            ", stateId=" + getStateId() +
            ", countryCode=" + getCountryCode() +
            ", stateCode=" + getStateCode() +
            "}";
    }
}
