package ae.rta.dls.backend.domain.prd;


import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import ae.rta.dls.backend.domain.util.BusinessProfileProductJsonConverter;
import ae.rta.dls.backend.domain.util.type.BusinessProfileProductJsonType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * BusinessProfile (prd_business_profile) entity.
 * @author Yousef Abu Amireh / Rami Nassar.
 */
@Entity
@Table(name = "prd_business_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessProfile extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buprSequenceGenerator")
    @SequenceGenerator(name = "buprSequenceGenerator", sequenceName = "bupr_seq", allocationSize = 1)
    private Long id;

    @Lob
    @Column(name = "product_document", nullable = false)
    @Convert(converter = BusinessProfileProductJsonConverter.class)
    private BusinessProfileProductJsonType productDocument;

    @Column(name = "remarks")
    private String remarks;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessProfileProductJsonType getProductDocument() {
        return productDocument;
    }

    public BusinessProfile productDocument(BusinessProfileProductJsonType productDocument) {
        this.productDocument = productDocument;
        return this;
    }

    public void setProductDocument(BusinessProfileProductJsonType productDocument) {
        this.productDocument = productDocument;
    }

    public String getRemarks() {
        return remarks;
    }

    public BusinessProfile remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessProfile businessProfile = (BusinessProfile) o;
        if (businessProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessProfile{" +
            "id=" + getId() +
            ", productDocument='" + getProductDocument() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
