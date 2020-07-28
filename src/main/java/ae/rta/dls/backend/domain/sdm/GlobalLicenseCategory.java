package ae.rta.dls.backend.domain.sdm;

import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import ae.rta.dls.backend.domain.util.MultilingualJsonConverter;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * The License Category Code entity.
 * @author Tariq Abu Amireh
 */
@ApiModel(description = "The Global License Category entity. @author Tariq Abu Amireh")
@Entity
@Table(name = "sdm_global_license_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GlobalLicenseCategory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gllcSequenceGenerator")
    @SequenceGenerator(name = "gllcSequenceGenerator", sequenceName = "gllc_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "synched_entity_id", unique = true)
    private Long synchedEntityId;

    @NotNull
    @Column(name = "name", nullable = false)
    @Type(type = "json")
    //@Convert(converter = MultilingualJsonConverter.class)
    private MultilingualJsonType name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public GlobalLicenseCategory code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getSynchedEntityId() {
        return synchedEntityId;
    }

    public GlobalLicenseCategory synchedEntityId(Long synchedEntityId) {
        this.synchedEntityId = synchedEntityId;
        return this;
    }

    public void setSynchedEntityId(Long synchedEntityId) {
        this.synchedEntityId = synchedEntityId;
    }

    public MultilingualJsonType getName() {
        return name;
    }

    public GlobalLicenseCategory name(MultilingualJsonType name) {
        this.name = name;
        return this;
    }

    public void setName(MultilingualJsonType name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GlobalLicenseCategory globalLicenseCategory = (GlobalLicenseCategory) o;
        if (globalLicenseCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), globalLicenseCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GlobalLicenseCategory{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", synchedEntityId=-" + getSynchedEntityId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
