package ae.rta.dls.backend.domain.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Driving License Category Details Json Type.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DrivingLicenseCategoryDetailJsonType implements Serializable {

    @JsonIgnore
    private Long id;

    @NotNull
    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private MultilingualJsonType name;

    @JsonProperty("issueDate")
    private LocalDate issueDate;

    @JsonProperty("sourceDetails")
    private DrivingLicenseSourceDetailsJsonType drivingLicenseSourceDetailsJsonType;

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

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public DrivingLicenseSourceDetailsJsonType getDrivingLicenseSourceDetailsJsonType() {
        return drivingLicenseSourceDetailsJsonType;
    }

    public void setDrivingLicenseSourceDetailsJsonType(DrivingLicenseSourceDetailsJsonType drivingLicenseSourceDetailsJsonType) {
        this.drivingLicenseSourceDetailsJsonType = drivingLicenseSourceDetailsJsonType;
    }

    @Override
    public String toString() {
        return "drivingLicenseCategoryDetailJsonType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", drivingLicenseSourceDetailsJsonType='" + getDrivingLicenseSourceDetailsJsonType() + "'" +
            "}";
    }
}
