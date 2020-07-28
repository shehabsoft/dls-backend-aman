package ae.rta.dls.backend.service.dto.prd;

import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


/**
 * Nationality DTO
 *
 * @author Mohammad Qasim
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NationalityDTO extends AbstractAuditingDTO implements Serializable {

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private MultilingualJsonType name;

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

    @Override
    public String toString() {
        return "NationalityDTO{" +
            ", code=" + getCode() +
            ", name=" + getName() +
            "}";
    }
}
