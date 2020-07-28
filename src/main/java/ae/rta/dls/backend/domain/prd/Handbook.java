package ae.rta.dls.backend.domain.prd;

import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import ae.rta.dls.backend.domain.util.HandbookProductJsonConverter;
import ae.rta.dls.backend.domain.type.HandbookProductJsonType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * HandBook (prd_hand_book) entity.
 * @author Mohammad Qasim.
 */
@Entity
@Table(name = "prd_hand_book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Handbook extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "haboSequenceGenerator")
    @SequenceGenerator(name = "haboSequenceGenerator", sequenceName = "habo_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "service_request_id", nullable = false)
    private Long serviceRequestId;

    @NotNull
    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    //@Lob
    @Column(name = "product_document", nullable = false)
    @Type(type = "json")
    //@Convert(converter = HandbookProductJsonConverter.class)
    private HandbookProductJsonType productDocument;

    @Column(name = "technical_remarks")
    private String technicalRemarks;

    @Column(name = "synched_entity_id", unique = true)
    private Long synchedEntityId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public Handbook serviceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
        return this;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public Handbook applicationId(Long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public HandbookProductJsonType getProductDocument() {
        return productDocument;
    }

    public Handbook productDocument(HandbookProductJsonType productDocument) {
        this.productDocument = productDocument;
        return this;
    }

    public void setProductDocument(HandbookProductJsonType productDocument) {
        this.productDocument = productDocument;
    }

    public String getTechnicalRemarks() {
        return technicalRemarks;
    }

    public Handbook technicalRemarks(String technicalRemarks) {
        this.technicalRemarks = technicalRemarks;
        return this;
    }

    public void setTechnicalRemarks(String technicalRemarks) {
        this.technicalRemarks = technicalRemarks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Handbook)) {
            return false;
        }
        return id != null && id.equals(((Handbook) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Handbook{" +
            "id=" + getId() +
            ", serviceRequestId=" + getServiceRequestId() +
            ", applicationId=" + getApplicationId() +
            ", productDocument='" + getProductDocument() + "'" +
            ", technicalRemarks='" + getTechnicalRemarks() + "'" +
            "}";
    }
}
