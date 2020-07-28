package ae.rta.dls.backend.service.dto.sys;

import ae.rta.dls.backend.domain.util.MultilingualJsonConverter;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Convert;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ApplicationConfiguration entity.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationConfigurationDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String configKey;

    @NotNull
    private String configValue;

    @NotNull
    @Type(type = "json")
    //@Convert(converter = MultilingualJsonConverter.class)
    private MultilingualJsonType description;

    @NotNull
    private Boolean cached;

    @NotNull
    private Boolean encrypted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public MultilingualJsonType getDescription() {
        return description;
    }

    public void setDescription(MultilingualJsonType description) {
        this.description = description;
    }

    public Boolean isCached() {
        return cached;
    }

    public void setCached(Boolean cached) {
        this.cached = cached;
    }

    public Boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationConfigurationDTO applicationConfigurationDTO = (ApplicationConfigurationDTO) o;
        if (applicationConfigurationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationConfigurationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationConfigurationDTO{" +
                "id=" + getId() +
                ", configKey='" + getConfigKey() + "'" +
                ", configValue='" + getConfigValue() + "'" +
                ", description='" + getDescription() + "'" +
                ", cached='" + isCached() + "'" +
                ", encrypted='" + isEncrypted() + "'" +
                "}";
    }
}
