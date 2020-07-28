package ae.rta.dls.backend.service.dto.trn;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;

/**
 * A DTO for the ApplicationPhase entity.
 */
@ApiModel(description = "ApplicationPhase (trn_application_phase) entity. @author Mena Emiel.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationPhaseDTO extends AbstractAuditingDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("phaseType")
    private PhaseType phaseType;

    @NotNull
    @JsonProperty("phaseSequence")
    private Integer phaseSequence;

    @NotNull
    @JsonProperty("startDate")
    private LocalDateTime startDate;

    @JsonProperty("endDate")
    private LocalDateTime endDate;

    @JsonProperty("persona")
    private String persona;

    private Long applicationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhaseType getPhaseType() {
        return phaseType;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public Integer getPhaseSequence() {
        return phaseSequence;
    }

    public void setPhaseSequence(Integer phaseSequence) {
        this.phaseSequence = phaseSequence;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationPhaseDTO applicationPhaseDTO = (ApplicationPhaseDTO) o;
        if (applicationPhaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationPhaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationPhaseDTO{" +
            "id=" + getId() +
            ", phaseType='" + getPhaseType() + "'" +
            ", phaseSequence=" + getPhaseSequence() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", persona='" + getPersona() + "'" +
            ", application=" + getApplicationId() +
            "}";
    }
}
