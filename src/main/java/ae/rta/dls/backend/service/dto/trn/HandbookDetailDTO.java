package ae.rta.dls.backend.service.dto.trn;

import ae.rta.dls.backend.domain.enumeration.trn.HandbookType;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the Application entity.
 */
@ApiModel(description = "Hand book service document. @author Yousef Abu Amireh.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HandbookDetailDTO extends AbstractAuditingDTO implements Serializable {

    @JsonProperty("name")
    private MultilingualJsonType name;

    @JsonProperty("type")
    private HandbookType type;

    public MultilingualJsonType getName() {
        return name;
    }

    public void setName(MultilingualJsonType name) {
        this.name = name;
    }

    public HandbookType getType() {
        return type;
    }

    public void setType(HandbookType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "HandbookDetailDTO{" +
            "name=" + getName() +
            ", type='" + getType() + "'" +
            "}";
    }
}
