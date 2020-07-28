package ae.rta.dls.backend.domain.sdm;

import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import ae.rta.dls.backend.domain.enumeration.sdm.LicenseCategoryStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The License Category entity.
 * @author Tariq Abu Amireh
 */
@ApiModel(description = "The License Category entity. @author Tariq Abu Amireh")
@Entity
@Table(name = "sdm_license_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LicenseCategory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "licaSequenceGenerator")
    @SequenceGenerator(name = "licaSequenceGenerator", sequenceName = "lica_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "synched_entity_id", unique = true)
    private Long synchedEntityId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LicenseCategoryStatus status;

    @ManyToOne
    @JsonIgnoreProperties("licenseCategories")
    private GlobalLicenseCategory globalLicenseCategory;

    @Column(name = "country_code", length = 3, nullable = false)
    private String countryCode;

    @Column(name = "city_code", length = 3, nullable = false)
    private String cityCode;

    @Column(name="uts_mapping_code")
    private Integer utsMappingCode;

    @Column(name="handbook_type")
    private String handbookType;

    @Column(name = "exchangeable")
    private Boolean exchangeable;

    @ManyToOne
    private LicenseCategory localLicenseCategory;

    @Column(name = "sort_order")
    private Integer sortOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public LicenseCategory code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getSynchedEntityId() {
        return synchedEntityId;
    }

    public LicenseCategory synchedEntityId(Long synchedEntityId) {
        this.synchedEntityId = synchedEntityId;
        return this;
    }

    public void setSynchedEntityId(Long synchedEntityId) {
        this.synchedEntityId = synchedEntityId;
    }

    public LicenseCategoryStatus getStatus() {
        return status;
    }

    public LicenseCategory status(LicenseCategoryStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(LicenseCategoryStatus status) {
        this.status = status;
    }

    public GlobalLicenseCategory getGlobalLicenseCategory() {
        return globalLicenseCategory;
    }

    public LicenseCategory globalLicenseCategory(GlobalLicenseCategory globalLicenseCategory) {
        this.globalLicenseCategory = globalLicenseCategory;
        return this;
    }

    public void setGlobalLicenseCategory(GlobalLicenseCategory globalLicenseCategory) {
        this.globalLicenseCategory = globalLicenseCategory;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public LicenseCategory countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getUtsMappingCode() {
        return utsMappingCode;
    }

    public void setUtsMappingCode(Integer utsMappingCode) {
        this.utsMappingCode = utsMappingCode;
    }

    public String getHandbookType() {
        return handbookType;
    }

    public void setHandbookType(String handbookType) {
        this.handbookType = handbookType;
    }

    public Boolean getExchangeable() {
        return exchangeable;
    }

    public void setExchangeable(Boolean exchangeable) {
        this.exchangeable = exchangeable;
    }

    public LicenseCategory cityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public LicenseCategory localLicenseCategory(LicenseCategory localLicenseCategory) {
        this.localLicenseCategory = localLicenseCategory;
        return this;
    }

    public LicenseCategory getLocalLicenseCategory() {
        return localLicenseCategory;
    }

    public void setLocalLicenseCategory(LicenseCategory localLicenseCategory) {
        this.localLicenseCategory = localLicenseCategory;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LicenseCategory licenseCategory = (LicenseCategory) o;
        if (licenseCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), licenseCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LicenseCategory{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", synchedEntityId=-" + getSynchedEntityId() +
            ", status=" + getStatus() +
            ", countryCode=" + getCountryCode() +
            ", cityCode=" + getCityCode() +
            ", utsMappingCode=" + getUtsMappingCode() +
            ", handbookType=" + getHandbookType() +
            ", sortOrder=" + getSortOrder() +
            "}";
    }
}
