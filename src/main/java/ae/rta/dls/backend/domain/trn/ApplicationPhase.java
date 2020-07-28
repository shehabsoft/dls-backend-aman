package ae.rta.dls.backend.domain.trn;


import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;

/**
 * ApplicationPhase (trn_application_phase) entity.
 * @author Mena Emiel.
 */
@Entity
@Table(name = "trn_application_phase")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationPhase extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "apphSequenceGenerator")
    @SequenceGenerator(name = "apphSequenceGenerator", sequenceName = "apph_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "phase_type", nullable = false)
    private PhaseType phaseType;

    @NotNull
    @Column(name = "phase_sequence", nullable = false)
    private Integer phaseSequence;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "persona")
    private String persona;

    @ManyToOne
    @JsonIgnoreProperties("applicationPhases")
    private Application application;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhaseType getPhaseType() {
        return phaseType;
    }

    public ApplicationPhase phaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
        return this;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public Integer getPhaseSequence() {
        return phaseSequence;
    }

    public ApplicationPhase sequence(Integer phaseSequence) {
        this.phaseSequence = phaseSequence;
        return this;
    }

    public void setPhaseSequence(Integer phaseSequence) {
        this.phaseSequence = phaseSequence;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public ApplicationPhase startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public ApplicationPhase endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getPersona() {
        return persona;
    }

    public ApplicationPhase persona(String persona) {
        this.persona = persona;
        return this;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public Application getApplication() {
        return application;
    }

    public ApplicationPhase application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
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
        ApplicationPhase applicationPhase = (ApplicationPhase) o;
        if (applicationPhase.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationPhase.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationPhase{" +
            "id=" + getId() +
            ", phaseType='" + getPhaseType() + "'" +
            ", phaseSequence=" + getPhaseSequence() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", persona='" + getPersona() + "'" +
            "}";
    }
}
