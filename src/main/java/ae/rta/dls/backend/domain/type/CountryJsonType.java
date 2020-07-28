package ae.rta.dls.backend.domain.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Country Json Type.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CountryJsonType implements Serializable {

    public static final String COUNTRY_UAE = "ARE";

    @JsonIgnore
    private Long id;

    @NotNull
    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private MultilingualJsonType name;

    @JsonProperty("countryStateDetails")
    private StateJsonType issuedFromStateDetails;

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

    public MultilingualJsonType getName() {
        return name;
    }

    public void setName(MultilingualJsonType name) {
        this.name = name;
    }

    public StateJsonType getIssuedFromStateDetails() {
        return issuedFromStateDetails;
    }

    public void setIssuedFromStateDetails(StateJsonType issuedFromStateDetails) {
        this.issuedFromStateDetails = issuedFromStateDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CountryJsonType countryJsonType = (CountryJsonType) o;
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
        return "CountryJsonType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", issuedFromStateDetails='" + getIssuedFromStateDetails() + "'" +
            "}";
    }
}
