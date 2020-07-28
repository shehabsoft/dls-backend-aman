package ae.rta.dls.backend.domain.trf;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Driving License entity.
 * @author Yousef.
 */
@Entity
@Table(name = "driving_license_view")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DrivingLicenseView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "emirates_id")
    private String emiratesId;

    @Column(name = "status")
    private Integer status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmiratesId() {
        return emiratesId;
    }

    public DrivingLicenseView emiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
        return this;
    }

    public void setEmiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
    }

    public Integer getStatus() {
        return status;
    }

    public DrivingLicenseView status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
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
        DrivingLicenseView drivingLicenseView = (DrivingLicenseView) o;
        if (drivingLicenseView.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drivingLicenseView.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DrivingLicenseView{" +
            "id=" + getId() +
            ", emiratesId='" + getEmiratesId() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
