package ae.rta.dls.backend.domain.trf;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * License Application entity.\n@author Ahmad Abo AlShamat.
 */
@Entity
@Table(name = "license_application_view")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LicenseApplicationView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "emirates_id")
    private String emiratesId;

    @Column(name = "vcl_id")
    private Integer vclId;

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

    public LicenseApplicationView emiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
        return this;
    }

    public void setEmiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
    }

    public Integer getVclId() {
        return vclId;
    }

    public LicenseApplicationView vclId(Integer vclId) {
        this.vclId = vclId;
        return this;
    }

    public void setVclId(Integer vclId) {
        this.vclId = vclId;
    }

    public Integer getStatus() {
        return status;
    }

    public LicenseApplicationView status(Integer status) {
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
        if (!(o instanceof LicenseApplicationView)) {
            return false;
        }
        return id != null && id.equals(((LicenseApplicationView) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LicenseApplicationView{" +
            "id=" + getId() +
            ", emiratesId='" + getEmiratesId() + "'" +
            ", vclId=" + getVclId() +
            ", status=" + getStatus() +
            "}";
    }
}
