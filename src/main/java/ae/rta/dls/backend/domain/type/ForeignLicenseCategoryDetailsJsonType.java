package ae.rta.dls.backend.domain.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Foreign License Category Details Json Type.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ForeignLicenseCategoryDetailsJsonType implements Serializable {

    @JsonProperty("globalCategory")
    private ForeignLicenseCategoryDetailJsonType globalCategory;

    @JsonProperty("localCategory")
    private ForeignLicenseCategoryDetailJsonType localCategory;

    @JsonProperty("issueDate")
    private LocalDate licenseIssueDate;

    public ForeignLicenseCategoryDetailJsonType getGlobalCategory() {
        return globalCategory;
    }

    public void setGlobalCategory(ForeignLicenseCategoryDetailJsonType globalCategory) {
        this.globalCategory = globalCategory;
    }

    public ForeignLicenseCategoryDetailJsonType getLocalCategory() {
        return localCategory;
    }

    public void setLocalCategory(ForeignLicenseCategoryDetailJsonType localCategory) {
        this.localCategory = localCategory;
    }

    public LocalDate getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(LocalDate licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }

    @Override
    public String toString() {
        return "ForeignLicenseCategoryDetailsJsonType{" +
            "globalCategory=" + getGlobalCategory() +
            ", localeCategory='" + getLocalCategory() + "'" +
            ", licenseIssueDate='" + getLicenseIssueDate() + "'" +
            "}";
    }
}
