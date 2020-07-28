package ae.rta.dls.backend.domain.type;

import ae.rta.dls.backend.service.dto.prd.CustomerInfoDTO;
import ae.rta.dls.backend.service.dto.trn.DrivingLicenseDetailDTO;
import ae.rta.dls.backend.service.dto.trn.ForeignLicenseDetailDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 * Foreign Driving License Product Json Type
 *
 * @author Mohammad Qasim
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DrivingLicenseProductJsonType implements Serializable {

    @JsonProperty("customerInfo")
    private CustomerInfoDTO customerInfoDTO;

    @JsonProperty("drivingLicenseDetails")
    private DrivingLicenseDetailDTO drivingLicenseDetailDTO;

    @JsonProperty("foreignLicenseDetails")
    private List<ForeignLicenseDetailDTO> foreignLicenseDetailDTO;

    public CustomerInfoDTO getCustomerInfoDTO() {
        return customerInfoDTO;
    }

    public void setCustomerInfoDTO(CustomerInfoDTO customerInfoDTO) {
        this.customerInfoDTO = customerInfoDTO;
    }

    public DrivingLicenseDetailDTO getDrivingLicenseDetailDTO() {
        return drivingLicenseDetailDTO;
    }

    public void setDrivingLicenseDetailDTO(DrivingLicenseDetailDTO drivingLicenseDetailDTO) {
        this.drivingLicenseDetailDTO = drivingLicenseDetailDTO;
    }

    public List<ForeignLicenseDetailDTO> getForeignLicenseDetailDTO() {
        return foreignLicenseDetailDTO;
    }

    public void setForeignLicenseDetailDTO(List<ForeignLicenseDetailDTO> foreignLicenseDetailDTO) {
        this.foreignLicenseDetailDTO = foreignLicenseDetailDTO;
    }

    @Override
    public String toString() {
        return "DrivingLicenseProductJsonType{" +
            "customerInfoDTO=" + customerInfoDTO +
            ", drivingLicenseDetailDTO=" + drivingLicenseDetailDTO +
            ", foreignLicenseDetailDTO=" + foreignLicenseDetailDTO +
            '}';
    }
}
