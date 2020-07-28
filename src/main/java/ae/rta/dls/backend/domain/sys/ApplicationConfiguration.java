package ae.rta.dls.backend.domain.sys;

import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * Application Configuration entity.
 *
 * @author Rami Nassar
 */
@ApiModel(description = "Application Configuration entity. @author Rami Nassar")
@Entity
@Table(name = "sys_application_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationConfiguration extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "apcoSequenceGenerator")
    @SequenceGenerator(name = "apcoSequenceGenerator", sequenceName = "apco_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "config_key", nullable = false, unique = true)
    private String configKey;

    @NotNull
    @Column(name = "config_value", nullable = false)
    private String configValue;

    @NotNull
    @Column(name = "description", nullable = false)
    @Type(type = "json")
    //@Convert(converter = MultilingualJsonConverter.class)
    private MultilingualJsonType description;

    @NotNull
    @Column(name = "cached", nullable = false)
    private Boolean cached;

    @NotNull
    @Column(name = "encrypted", nullable = false)
    private Boolean encrypted;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigKey() {
        return configKey;
    }

    public ApplicationConfiguration configKey(String configKey) {
        this.configKey = configKey;
        return this;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public ApplicationConfiguration configValue(String configValue) {
        this.configValue = configValue;
        return this;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public MultilingualJsonType getDescription() {
        return description;
    }

    public ApplicationConfiguration description(MultilingualJsonType description) {
        this.description = description;
        return this;
    }

    public void setDescription(MultilingualJsonType description) {
        this.description = description;
    }

    public Boolean isCached() {
        return cached;
    }

    public ApplicationConfiguration cached(Boolean cached) {
        this.cached = cached;
        return this;
    }

    public void setCached(Boolean cached) {
        this.cached = cached;
    }

    public Boolean isEncrypted() {
        return encrypted;
    }

    public ApplicationConfiguration encrypted(Boolean encrypted) {
        this.encrypted = encrypted;
        return this;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
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
        ApplicationConfiguration applicationConfiguration = (ApplicationConfiguration) o;
        if (applicationConfiguration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationConfiguration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationConfiguration{" +
            "id=" + getId() +
            ", configKey='" + getConfigKey() + "'" +
            ", configValue='" + getConfigValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", cached='" + isCached() + "'" +
            ", encrypted='" + isEncrypted() + "'" +
            "}";
    }
}
