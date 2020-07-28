package ae.rta.dls.backend.domain.prd;


import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import ae.rta.dls.backend.domain.util.DrivingLicenseProductJsonConverter;
import ae.rta.dls.backend.domain.type.DrivingLicenseProductJsonType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * DrivingLicense (prd_driving_license) entity.
 * @author Mena Emiel.
 */
@Entity
@Table(name = "prd_driving_license")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DrivingLicense extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drliSequenceGenerator")
    @SequenceGenerator(name = "drliSequenceGenerator", sequenceName = "drli_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "service_request_id", nullable = false)
    private Long serviceRequestId;

    @NotNull
    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    @Column(name = "synched_entity_id", unique = true)
    private String synchedEntityId;

    //@Lob
    @Column(name = "product_document", nullable = false)
    @Type(type = "json")
    //@Convert(converter = DrivingLicenseProductJsonConverter.class)
    private DrivingLicenseProductJsonType productDocument;

    @Column(name = "technical_remarks")
    private String technicalRemarks;

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

    public DrivingLicense serviceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
        return this;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public DrivingLicense applicationId(Long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public DrivingLicenseProductJsonType getProductDocument() {
        return productDocument;
    }

    public DrivingLicense productDocument(DrivingLicenseProductJsonType productDocument) {
        this.productDocument = productDocument;
        return this;
    }

    public void setProductDocument(DrivingLicenseProductJsonType productDocument) {
        this.productDocument = productDocument;
    }

    public String getTechnicalRemarks() {
        return technicalRemarks;
    }

    public DrivingLicense technicalRemarks(String technicalRemarks) {
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DrivingLicense drivingLicense = (DrivingLicense) o;
        if (drivingLicense.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drivingLicense.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DrivingLicense{" +
            "id=" + getId() +
            ", serviceRequestId=" + getServiceRequestId() +
            ", applicationId=" + getApplicationId() +
            ", productDocument='" + getProductDocument() + "'" +
            ", technicalRemarks='" + getTechnicalRemarks() + "'" +
            "}";
    }
}
