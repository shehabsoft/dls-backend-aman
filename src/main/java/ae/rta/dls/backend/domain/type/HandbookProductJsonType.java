package ae.rta.dls.backend.domain.type;

import ae.rta.dls.backend.service.dto.prd.CustomerInfoDTO;
import ae.rta.dls.backend.service.dto.trn.HandbookDetailDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Handbook Product Json Type
 *
 * @author Mohammad Qasim
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HandbookProductJsonType  implements Serializable {

    @JsonProperty("customerInfo")
    private CustomerInfoDTO customerInfoDTO;

    @JsonProperty("handbookDetails")
    private HandbookDetailDTO handbookDetails;

    public CustomerInfoDTO getCustomerInfoDTO() {
        return customerInfoDTO;
    }

    public void setCustomerInfoDTO(CustomerInfoDTO customerInfoDTO) {
        this.customerInfoDTO = customerInfoDTO;
    }

    public HandbookDetailDTO getHandbookDetails() {
        return handbookDetails;
    }

    public void setHandbookDetails(HandbookDetailDTO handbookDetails) {
        this.handbookDetails = handbookDetails;
    }

    @Override
    public String toString() {
        return "HandbookProductJsonType{" +
            "customerInfoDTO=" + getCustomerInfoDTO() +
            ", handbookDetails=" + getHandbookDetails() +
            '}';
    }
}
