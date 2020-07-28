package ae.rta.dls.backend.service.dto.sys;

import ae.rta.dls.backend.domain.enumeration.sys.JobStatus;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AutomatedJobConfig entity.
 */
@ApiModel(description = "The Automated Job Configuration entity. @author Tariq Abu Amireh")
public class AutomatedJobConfigDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String jobName;

    private String cron;

    private Long fixedDelay;

    private Long initialDelay;

    private Long fixedRate;

    @NotNull
    private JobStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Long getFixedDelay() {
        return fixedDelay;
    }

    public void setFixedDelay(Long fixedDelay) {
        this.fixedDelay = fixedDelay;
    }

    public Long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(Long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public Long getFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(Long fixedRate) {
        this.fixedRate = fixedRate;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutomatedJobConfigDTO automatedJobConfigDTO = (AutomatedJobConfigDTO) o;
        if (automatedJobConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), automatedJobConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutomatedJobConfigDTO{" +
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
