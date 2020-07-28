package ae.rta.dls.backend.service.dto.prd;
import ae.rta.dls.backend.domain.util.type.BusinessProfileProductJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the BusinessProfile entity.
 */
@ApiModel(description = "BusinessProfile (prd_business_profile) entity. @author Yousef Abu Amireh / Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BusinessProfileDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;
    
    @Lob
    private BusinessProfileProductJsonType productDocument;

    private String remarks;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessProfileProductJsonType getProductDocument() {
        return productDocument;
    }

    public void setProductDocument(BusinessProfileProductJsonType productDocument) {
        this.productDocument = productDocument;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusinessProfileDTO businessProfileDTO = (BusinessProfileDTO) o;
        if (businessProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessProfileDTO{" +
            "id=" + getId() +
            ", productDocument='" + getProductDocument() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
