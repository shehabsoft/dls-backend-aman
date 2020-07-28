package ae.rta.dls.backend.domain.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * State Json Type.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StateJsonType implements Serializable {

    @JsonIgnore
    private Long id;

    @NotNull
    @JsonProperty("code")
    private String code;

    @NotNull
    @JsonProperty("name")
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

    public void setCode(String code) {
        this.code = code;
    }

    public MultilingualJsonType getName() {
        return name;
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

        StateJsonType stateJsonType = (StateJsonType) o;
        if (stateJsonType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stateJsonType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StateJsonType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
