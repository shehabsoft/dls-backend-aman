package ae.rta.dls.backend.domain.type;

import ae.rta.dls.backend.domain.enumeration.prd.DrivingLicenseSource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Driving License Category Details Json Type.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DrivingLicenseSourceDetailsJsonType implements Serializable {

    @JsonIgnore
    private Long id;

    @NotNull
    @JsonProperty("code")
    private DrivingLicenseSource code;

    @JsonProperty("name")
    private MultilingualJsonType name;

    @JsonProperty("referenceId")
    private Long referenceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DrivingLicenseSource getCode() {
        return code;
    }

    public void setCode(DrivingLicenseSource code) {
        this.code = code;
    }

    public MultilingualJsonType getName() {
        return name;
    }

    public void setName(MultilingualJsonType name) {
        this.name = name;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DrivingLicenseSourceDetailsJsonType countryJsonType = (DrivingLicenseSourceDetailsJsonType) o;
        if (countryJsonType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countryJsonType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "drivingLicenseSourceDetailsJsonType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", code='" + getReferenceId() + "'" +
            "}";
    }
}
