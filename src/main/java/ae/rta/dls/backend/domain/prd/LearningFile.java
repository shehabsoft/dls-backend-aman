package ae.rta.dls.backend.domain.prd;


import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import ae.rta.dls.backend.domain.util.LearningFileProductJsonConverter;
import ae.rta.dls.backend.domain.type.LearningFileProductJsonType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * LearningFile (prd_learning_file) entity.
 * @author Mena Emiel.
 */
@Entity
@Table(name = "prd_learning_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LearningFile extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lefiSequenceGenerator")
    @SequenceGenerator(name = "lefiSequenceGenerator", sequenceName = "lefi_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "persona_category_code", nullable = false)
    private String personaCategoryCode;

    @NotNull
    @Column(name = "persona_version_code", nullable = false)
    private String personaVersionCode;

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
    //@Convert(converter = LearningFileProductJsonConverter.class)
    @Type(type = "json")
    private LearningFileProductJsonType productDocument;

    @Column(name = "technical_remarks")
    private String technicalRemarks;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonaCategoryCode() {
        return personaCategoryCode;
    }

    public LearningFile personaCategoryCode(String personaCategoryCode) {
        this.personaCategoryCode = personaCategoryCode;
        return this;
    }

    public void setPersonaCategoryCode(String personaCategoryCode) {
        this.personaCategoryCode = personaCategoryCode;
    }

    public String getPersonaVersionCode() {
        return personaVersionCode;
    }

    public LearningFile personaVersionCode(String personaVersionCode) {
        this.personaVersionCode = personaVersionCode;
        return this;
    }

    public void setPersonaVersionCode(String personaVersionCode) {
        this.personaVersionCode = personaVersionCode;
    }

    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public LearningFile serviceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
        return this;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public LearningFile applicationId(Long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public LearningFileProductJsonType getProductDocument() {
        return productDocument;
    }

    public LearningFile productDocument(LearningFileProductJsonType productDocument) {
        this.productDocument = productDocument;
        return this;
    }

    public void setProductDocument(LearningFileProductJsonType productDocument) {
        this.productDocument = productDocument;
    }

    public String getTechnicalRemarks() {
        return technicalRemarks;
    }

    public LearningFile technicalRemarks(String technicalRemarks) {
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
        LearningFile learningFile = (LearningFile) o;
        if (learningFile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), learningFile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LearningFile{" +
            "id=" + getId() +
            ", personaCategoryCode='" + getPersonaCategoryCode() + "'" +
            ", personaVersionCode='" + getPersonaVersionCode() + "'" +
            ", serviceRequestId=" + getServiceRequestId() +
            ", applicationId=" + getApplicationId() +
            ", productDocument='" + getProductDocument() + "'" +
            ", technicalRemarks='" + getTechnicalRemarks() + "'" +
            "}";
    }
}
