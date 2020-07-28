package ae.rta.dls.backend.domain.sys;


import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import ae.rta.dls.backend.domain.enumeration.sys.JobStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Automated Job Configuration entity.
 * @author Tariq Abu Amireh
 */
@ApiModel(description = "The Automated Job Configuration entity. @author Tariq Abu Amireh")
@Entity
@Table(name = "sys_automated_job_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AutomatedJobConfig extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aujcSequenceGenerator")
    @SequenceGenerator(name = "aujcSequenceGenerator", sequenceName = "aujc_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "job_name", nullable = false, unique = true)
    private String jobName;

    @Column(name = "cron")
    private String cron;

    @Column(name = "fixed_delay")
    private Long fixedDelay;

    @Column(name = "initial_delay")
    private Long initialDelay;

    @Column(name = "fixed_rate")
    private Long fixedRate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status;

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

    public AutomatedJobConfig jobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCron() {
        return cron;
    }

    public AutomatedJobConfig cron(String cron) {
        this.cron = cron;
        return this;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Long getFixedDelay() {
        return fixedDelay;
    }

    public AutomatedJobConfig fixedDelay(Long fixedDelay) {
        this.fixedDelay = fixedDelay;
        return this;
    }

    public void setFixedDelay(Long fixedDelay) {
        this.fixedDelay = fixedDelay;
    }

    public Long getInitialDelay() {
        return initialDelay;
    }

    public AutomatedJobConfig initialDelay(Long initialDelay) {
        this.initialDelay = initialDelay;
        return this;
    }

    public void setInitialDelay(Long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public Long getFixedRate() {
        return fixedRate;
    }

    public AutomatedJobConfig fixedRate(Long fixedRate) {
        this.fixedRate = fixedRate;
        return this;
    }

    public void setFixedRate(Long fixedRate) {
        this.fixedRate = fixedRate;
    }
    public JobStatus getStatus() {
        return status;
    }

    public AutomatedJobConfig status(JobStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
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
        AutomatedJobConfig automatedJobConfig = (AutomatedJobConfig) o;
        if (automatedJobConfig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), automatedJobConfig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutomatedJobConfig{" +
            "id=" + getId() +
            ", jobName='" + getJobName() + "'" +
            ", cron='" + getCron() + "'" +
            ", fixedDelay=" + getFixedDelay() +
            ", initialDelay=" + getInitialDelay() +
            ", fixedRate=" + getFixedRate() +
            ", status=" + getStatus() +
            "}";
    }
}
