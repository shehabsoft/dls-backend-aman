package ae.rta.dls.backend.domain.type;

import ae.rta.dls.backend.service.dto.prd.CustomerInfoDTO;
import ae.rta.dls.backend.service.dto.trn.LearningFileDetailDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Learning File Product Json Type
 *
 * @author Mohammad Qasim
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LearningFileProductJsonType implements Serializable {

    @JsonProperty("customerInfo")
    private CustomerInfoDTO customerInfoDTO;

    @JsonProperty("learningFileDetails")
    private LearningFileDetailDTO learningFileDetailDTO;

    public CustomerInfoDTO getCustomerInfoDTO() {
        return customerInfoDTO;
    }

    public void setCustomerInfoDTO(CustomerInfoDTO customerInfoDTO) {
        this.customerInfoDTO = customerInfoDTO;
    }

    public LearningFileDetailDTO getLearningFileDetailDTO() {
        return learningFileDetailDTO;
    }

    public void setLearningFileDetailDTO(LearningFileDetailDTO learningFileDetailDTO) {
        this.learningFileDetailDTO = learningFileDetailDTO;
    }


    @Override
    public String toString() {
        return "LearningFileProductJsonType{" +
            "customerInfoDTO=" + customerInfoDTO +
            ", learningFileDetailDTO=" + learningFileDetailDTO +
            '}';
    }
}
