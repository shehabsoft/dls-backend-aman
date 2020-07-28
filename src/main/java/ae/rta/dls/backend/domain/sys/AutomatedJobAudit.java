package ae.rta.dls.backend.domain.sys;


import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Automated Job Audit entity.
 * @author Tariq Abu Amireh
 */
@Entity
@Table(name = "sys_automated_job_audit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AutomatedJobAudit extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aujaSequenceGenerator")
    @SequenceGenerator(name = "aujaSequenceGenerator", sequenceName = "auja_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "job_name", nullable = false)
    private String jobName;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "duration_in_seconds")
    private Long durationInSeconds;

    @Column(name = "cron")
    private String cron;

    @Column(name = "fixed_delay")
    private Long fixedDelay;

    @Column(name = "initial_delay")
    private Long initialDelay;

    @Column(name = "fixed_rate")
    private Long fixedRate;

    @Column(name = "technical_remarks")
    private String technicalRemarks;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public AutomatedJobAudit jobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public AutomatedJobAudit startTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public AutomatedJobAudit endTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getDurationInSeconds() {
        return durationInSeconds;
    }

    public AutomatedJobAudit durationInSeconds(Long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
        return this;
    }

    public void setDurationInSeconds(Long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public String getCron() {
        return cron;
    }

    public AutomatedJobAudit cron(String cron) {
        this.cron = cron;
        return this;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Long getFixedDelay() {
        return fixedDelay;
    }

    public AutomatedJobAudit fixedDelay(Long fixedDelay) {
        this.fixedDelay = fixedDelay;
        return this;
    }

    public void setFixedDelay(Long fixedDelay) {
        this.fixedDelay = fixedDelay;
    }

    public Long getInitialDelay() {
        return initialDelay;
    }

    public AutomatedJobAudit initialDelay(Long initialDelay) {
        this.initialDelay = initialDelay;
        return this;
    }

    public void setInitialDelay(Long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public Long getFixedRate() {
        return fixedRate;
    }

    public AutomatedJobAudit fixedRate(Long fixedRate) {
        this.fixedRate = fixedRate;
        return this;
    }

    public void setFixedRate(Long fixedRate) {
        this.fixedRate = fixedRate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getTechnicalRemarks() { return technicalRemarks; }

    public void setTechnicalRemarks(String technicalRemarks) { this.technicalRemarks = technicalRemarks; }

    public AutomatedJobAudit technicalRemarks(String technicalRemarks) {
        this.technicalRemarks = technicalRemarks;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AutomatedJobAudit automatedJobAudit = (AutomatedJobAudit) o;
        if (automatedJobAudit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), automatedJobAudit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutomatedJobAudit{" +
            "id=" + getId() +
            ", jobName='" + getJobName() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", durationInSeconds=" + getDurationInSeconds() +
            ", cron='" + getCron() + "'" +
            ", fixedDelay=" + getFixedDelay() +
            ", initialDelay=" + getInitialDelay() +
            ", fixedRate=" + getFixedRate() +
            ", technicalRemarks=" + getTechnicalRemarks() +
            "}";
    }
}
