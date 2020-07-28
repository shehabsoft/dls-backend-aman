package ae.rta.dls.backend.service.dto.prd;

import ae.rta.dls.backend.domain.prd.Handbook;
import ae.rta.dls.backend.domain.type.HandbookProductJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link Handbook} entity.
 */
@ApiModel(description = "HandBook (prd_hand_book) entity. @author Mohammad Qasim.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HandbookDTO extends AbstractAuditingDTO implements Serializable {

    @JsonIgnore
    private Long id;

    @NotNull
    @JsonProperty("serviceReferenceNo")
    private Long serviceRequestId;

    @NotNull
    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @Lob
    @JsonProperty("productDocument")
    private HandbookProductJsonType productDocument;

    @JsonProperty("technicalRemarks")
    private String technicalRemarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public HandbookProductJsonType getProductDocument() {
        return productDocument;
    }

    public void setProductDocument(HandbookProductJsonType productDocument) {
        this.productDocument = productDocument;
    }

    public String getTechnicalRemarks() {
        return technicalRemarks;
    }

    public void setTechnicalRemarks(String technicalRemarks) {
        this.technicalRemarks = technicalRemarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HandbookDTO handbookDTO = (HandbookDTO) o;
        if (handbookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), handbookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HandbookDetailDTO{" +
            "id=" + getId() +
            ", serviceRequestId=" + getServiceRequestId() +
            ", applicationId=" + getApplicationId() +
            ", productDocument='" + getProductDocument() + "'" +
            ", technicalRemarks='" + getTechnicalRemarks() + "'" +
            "}";
    }
}
