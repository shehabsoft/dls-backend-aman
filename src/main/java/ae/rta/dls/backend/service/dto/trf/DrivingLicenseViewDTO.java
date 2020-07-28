package ae.rta.dls.backend.service.dto.trf;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DrivingLicenseView entity.
 */
@ApiModel(description = "Driving License entity. @author Yousef.")
public class DrivingLicenseViewDTO implements Serializable {

    private Long id;

    private String emiratesId;

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

        DrivingLicenseViewDTO drivingLicenseViewDTO = (DrivingLicenseViewDTO) o;
        if (drivingLicenseViewDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drivingLicenseViewDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DrivingLicenseViewDTO{" +
            "id=" + getId() +
            ", emiratesId='" + getEmiratesId() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
