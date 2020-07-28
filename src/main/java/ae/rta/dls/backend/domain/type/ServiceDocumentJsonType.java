package ae.rta.dls.backend.domain.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Service Document Json Type.
 *
 * This type was developed to service document generic attributes for any service request.
 *
 * @author Mena Emiel
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceDocumentJsonType implements Serializable {

    /** Generic service parameters to serve any kind of service request */
    @JsonProperty("parameters")
    @NotNull
    private Object parameters;


    public Object getParameters() {
        return parameters;
    }

    public void setParameters(Object parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "ServiceDocumentJsonType{" +
            "parameters=" + getParameters() +
            "}";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(parameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceDocumentJsonType jsonType = (ServiceDocumentJsonType) o;
        if (jsonType.getParameters() == null || getParameters() == null) {
            return false;
        }
        return Objects.equals(getParameters().toString(), jsonType.getParameters().toString());
    }
}
