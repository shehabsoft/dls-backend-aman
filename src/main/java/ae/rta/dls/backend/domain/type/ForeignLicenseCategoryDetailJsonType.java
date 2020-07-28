package ae.rta.dls.backend.domain.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Foreign License Category Detail Json Type.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ForeignLicenseCategoryDetailJsonType implements Serializable {

    @JsonIgnore
    private Long id;

    @NotNull
    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private MultilingualJsonType name;

    @JsonProperty("statusDate")
    private LocalDateTime statusDate;

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

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    @Override
    public String toString() {
        return "foreignLicenseCategoryDetailJsonType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", statusDate='" + getStatusDate() + "'" +
            "}";
    }
}
