package ae.rta.dls.backend.domain.sys;

import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

/**
 * Error Log entity.
 * @author Mena Emiel.
 */
@ApiModel(description = "Error Log entity. @author Mena Emiel.")
@Entity
@Table(name = "sys_error_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ErrorLog extends AbstractAuditingEntity {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "erloSeqGenerator")
    @SequenceGenerator(name = "erloSeqGenerator", sequenceName = "erlo_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "source", nullable = false)
    private String source;

    @Lob
    @Column(name = "cause")
    private String cause;

    @Lob
    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "application_id")
    private Long applicationId;

    public ErrorLog() {
        //Default Constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public ErrorLog source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCause() {
        return cause;
    }

    public ErrorLog cause(String cause) {
        this.cause = cause;
        return this;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public ErrorLog message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
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
        ErrorLog errorLog = (ErrorLog) o;
        if (errorLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), errorLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ErrorLog{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", cause='" + getCause() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
