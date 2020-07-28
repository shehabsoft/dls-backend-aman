package ae.rta.dls.backend.service.dto.sys;

import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO for the AutomatedJobAudit entity.
 */
@ApiModel(description = "The Automated Job Audit entity. @author Tariq Abu Amireh")
public class AutomatedJobAuditDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String jobName;

    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long durationInSeconds;

    private String cron;

    private Long fixedDelay;

    private Long initialDelay;

    private Long fixedRate;

    private String technicalRemarks;


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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
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

    public String getTechnicalRemarks() { return technicalRemarks; }

    public void setTechnicalRemarks(String remarks) { this.technicalRemarks = remarks; }


    /**
     * Default Constructor
     */
    public AutomatedJobAuditDTO() {
        this.jobName = "";
        this.startTime = LocalDateTime.now();
    }

    public AutomatedJobAuditDTO(String jobName , String technicalRemarks) {
        this.jobName = jobName;
        this.startTime = LocalDateTime.now();
        setTechnicalRemarks(technicalRemarks);
    }


    public AutomatedJobAuditDTO(AutomatedJobConfigDTO automatedJobConfigDTO) {
        if (automatedJobConfigDTO == null) {
            this.jobName = "";
            this.startTime = LocalDateTime.now();

            return;
        }

        this.jobName = automatedJobConfigDTO.getJobName();
        this.startTime = LocalDateTime.now();
        setCron(automatedJobConfigDTO.getCron());
        setInitialDelay(automatedJobConfigDTO.getInitialDelay());
        setFixedDelay(automatedJobConfigDTO.getFixedDelay());
        setFixedRate(automatedJobConfigDTO.getFixedRate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutomatedJobAuditDTO automatedJobAuditDTO = (AutomatedJobAuditDTO) o;
        if (automatedJobAuditDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), automatedJobAuditDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutomatedJobAuditDTO{" +
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
