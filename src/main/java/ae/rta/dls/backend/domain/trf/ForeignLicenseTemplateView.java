package ae.rta.dls.backend.domain.trf;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Foreign License Template entity.
 * @author Yousef.
 */
@Entity
@Table(name = "held_license_template_view")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ForeignLicenseTemplateView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;

    @Lob
    @Column(name = "back_side_template")
    private String backSideTemplate;

    @Lob
    @Column(name = "front_side_template")
    private String frontSideTemplate;

    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "state_code")
    private String stateCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArabicName() {
        return arabicName;
    }

    public ForeignLicenseTemplateView arabicName(String arabicName) {
        this.arabicName = arabicName;
        return this;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public ForeignLicenseTemplateView englishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getBackSideTemplate() {
        return backSideTemplate;
    }

    public ForeignLicenseTemplateView backSideTemplate(String backSideTemplate) {
        this.backSideTemplate = backSideTemplate;
        return this;
    }

    public void setBackSideTemplate(String backSideTemplate) {
        this.backSideTemplate = backSideTemplate;
    }

    public String getFrontSideTemplate() {
        return frontSideTemplate;
    }

    public ForeignLicenseTemplateView frontSideTemplate(String frontSideTemplate) {
        this.frontSideTemplate = frontSideTemplate;
        return this;
    }

    public void setFrontSideTemplate(String frontSideTemplate) {
        this.frontSideTemplate = frontSideTemplate;
    }

    public Long getCountryId() {
        return countryId;
    }

    public ForeignLicenseTemplateView countryId(Long countryId) {
        this.countryId = countryId;
        return this;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getStateId() {
        return stateId;
    }

    public ForeignLicenseTemplateView stateId(Long stateId) {
        this.stateId = stateId;
        return this;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


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
        ForeignLicenseTemplateView foreignLicenseTemplateView = (ForeignLicenseTemplateView) o;
        if (foreignLicenseTemplateView.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foreignLicenseTemplateView.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HeldLicenseTemplateView{" +
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
