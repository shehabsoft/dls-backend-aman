package ae.rta.dls.backend.service.dto.trn;

import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the Application entity.
 */
@ApiModel(description = "Foreign License service document. @author Mena Emiel.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ForeignLicenseDTO extends AbstractAuditingDTO implements Serializable {

    @NotNull
    @JsonProperty("issuedByApplicationNo")
    private Long issuedByApplicationNo;

    @NotNull
    @JsonProperty("handicaped")
    private boolean handicaped;


    public Long getIssuedByApplicationNo() {
        return issuedByApplicationNo;
    }

    public void setIssuedByApplicationNo(Long issuedByApplicationNo) {
        this.issuedByApplicationNo = issuedByApplicationNo;
    }

    public boolean isHandicaped() {
        return handicaped;
    }

    public void setHandicaped(boolean handicaped) {
        this.handicaped = handicaped;
    }
}
