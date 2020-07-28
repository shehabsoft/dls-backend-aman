package ae.rta.dls.backend.service.dto.sdm;

import ae.rta.dls.backend.domain.enumeration.sdm.LicenseCategoryStatus;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the LicenseCategory entity.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LicenseCategoryDTO extends AbstractAuditingDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("code")
    private String code;

    @NotNull
    @JsonProperty("status")
    private LicenseCategoryStatus status;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("cityCode")
    private String cityCode;

    @JsonProperty("licenseCategory")
    private GlobalLicenseCategoryDTO globalLicenseCategoryDTO;

    @JsonIgnore
    private Long globalLicenseCategoryId;

    @JsonProperty("localLicenseCategory")
    private LicenseCategoryDTO localLicenseCategoryDTO;

    @JsonIgnore
    private Long localLicenseCategoryId;

    @JsonIgnore
    private Integer utsMappingCode;

    @JsonProperty("handbookType")
    private String handbookType;

    @JsonProperty("sortOrder")
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

    public void setCode(String code) {
        this.code = code;
    }

    public LicenseCategoryStatus getStatus() {
        return status;
    }

    public void setStatus(LicenseCategoryStatus status) {
        this.status = status;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public GlobalLicenseCategoryDTO getGlobalLicenseCategoryDTO() {
        return globalLicenseCategoryDTO;
    }

    public void setGlobalLicenseCategoryDTO(GlobalLicenseCategoryDTO globalLicenseCategoryDTO) {
        this.globalLicenseCategoryDTO = globalLicenseCategoryDTO;
    }

    public Long getGlobalLicenseCategoryId() {
        return globalLicenseCategoryId;
    }

    public void setGlobalLicenseCategoryId(Long globalLicenseCategoryId) {
        this.globalLicenseCategoryId = globalLicenseCategoryId;
    }

    public LicenseCategoryDTO getLocalLicenseCategoryDTO() {
        return localLicenseCategoryDTO;
    }

    public void setLocalLicenseCategoryDTO(LicenseCategoryDTO localLicenseCategoryDTO) {
        this.localLicenseCategoryDTO = localLicenseCategoryDTO;
    }

    public Long getLocalLicenseCategoryId() {
        return localLicenseCategoryId;
    }

    public void setLocalLicenseCategoryId(Long localLicenseCategoryId) {
        this.localLicenseCategoryId = localLicenseCategoryId;
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

        LicenseCategoryDTO licenseCategoryDTO = (LicenseCategoryDTO) o;
        if (licenseCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), licenseCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LicenseCategoryDTO{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", status=" + getStatus() +
            ", globalLicenseCategoryDTO=" + getGlobalLicenseCategoryDTO() +
            ", globalLicenseCategoryId=" + getGlobalLicenseCategoryId() +
            ", countryCode=" + getCountryCode() +
            ", cityCode=" + getCityCode() +
            ", localLicenseCategoryDTO=" + getLocalLicenseCategoryDTO() +
            ", localLicenseCategoryId=" + getLocalLicenseCategoryId() +
            ", utsMappingCode=" + getUtsMappingCode() +
            ", handbookType=" + getHandbookType() +
            ", sortOrder=" + getSortOrder() +
            "}";
    }
}
