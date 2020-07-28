package ae.rta.dls.backend.domain.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Driving Lessons Json Type.
 *
 * @author Yousef Abu Amireh.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DrivingLessonsJsonType implements Serializable {

    @JsonProperty("required")
    private Boolean required;

    @JsonProperty("lessonsRequired")
    private Integer lessonsRequired;

    @JsonProperty("lessonsAttended")
    private Integer lessonsAttended;

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getLessonsRequired() {
        return lessonsRequired;
    }

    public void setLessonsRequired(Integer lessonsRequired) {
        this.lessonsRequired = lessonsRequired;
    }

    public Integer getLessonsAttended() {
        return lessonsAttended;
    }

    public void setLessonsAttended(Integer lessonsAttended) {
        this.lessonsAttended = lessonsAttended;
    }

    @Override
    public String toString() {
        return "DrivingLessonsJsonType{" +
            ", required='" + getRequired() + "'" +
            ", lessonsRequired='" + getLessonsRequired() + "'" +
            ", lessonsAttended='" + getLessonsAttended() + "'" +
            "}";
    }
}
