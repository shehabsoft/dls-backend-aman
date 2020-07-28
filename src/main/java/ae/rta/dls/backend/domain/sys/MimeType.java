package ae.rta.dls.backend.domain.sys;


import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Mime Type entity.
 * @author Mohammad Abu Lawi.
 */
@Entity
@Table(name = "sys_mime_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MimeType extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mitySequenceGenerator")
    @SequenceGenerator(name = "mitySequenceGenerator", sequenceName = "mity_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "extension", nullable = false, unique = true)
    private String extension;

    @NotNull
    @Column(name = "content_type", nullable = false)
    private String contentType;

    @NotNull
    @Column(name = "maximum_size", nullable = false)
    private Integer maximumSize;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public MimeType extension(String extension) {
        this.extension = extension;
        return this;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getContentType() {
        return contentType;
    }

    public MimeType contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getMaximumSize() {
        return maximumSize;
    }

    public MimeType maximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
        return this;
    }

    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
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
        MimeType mimeType = (MimeType) o;
        if (mimeType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mimeType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MimeType{" +
            "id=" + getId() +
            ", extension='" + getExtension() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", maximumSize=" + getMaximumSize() +
            "}";
    }
}
