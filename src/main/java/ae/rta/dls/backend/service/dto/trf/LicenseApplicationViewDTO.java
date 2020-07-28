package ae.rta.dls.backend.service.dto.trf;
import ae.rta.dls.backend.domain.trf.LicenseApplicationView;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link LicenseApplicationView} entity.
 */
@ApiModel(description = "License Application entity.\n@author Ahmad Abo AlShamat.")
public class LicenseApplicationViewDTO implements Serializable {

    private Long id;

    private String emiratesId;

    private Integer vclId;

    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmiratesId() {
        return emiratesId;
    }

    public void setEmiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
    }

    public Integer getVclId() {
        return vclId;
    }

    public void setVclId(Integer vclId) {
        this.vclId = vclId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

        LicenseApplicationViewDTO licenseApplicationViewDTO = (LicenseApplicationViewDTO) o;
        if (licenseApplicationViewDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), licenseApplicationViewDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LicenseApplicationViewDTO{" +
            "id=" + getId() +
            ", emiratesId='" + getEmiratesId() + "'" +
            ", vclId=" + getVclId() +
            ", status=" + getStatus() +
            "}";
    }
}
