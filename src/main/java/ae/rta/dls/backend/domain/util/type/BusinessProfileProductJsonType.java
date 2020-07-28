package ae.rta.dls.backend.domain.util.type;

import ae.rta.dls.backend.service.dto.prd.CustomerInfoDTO;
import ae.rta.dls.backend.service.dto.prd.ProductDetailsDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Business Profile Product Json Type
 *
 * @author Rami Nassar
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BusinessProfileProductJsonType implements Serializable {

    @JsonProperty("customerInfo")
    private CustomerInfoDTO customerInfoDTO;

    @JsonProperty("productDetails")
    private ProductDetailsDTO productDetails;

    public CustomerInfoDTO getCustomerInfoDTO() {
        return customerInfoDTO;
    }

    public void setCustomerInfoDTO(CustomerInfoDTO customerInfoDTO) {
        this.customerInfoDTO = customerInfoDTO;
    }

    public ProductDetailsDTO getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetailsDTO productDetails) {
        this.productDetails = productDetails;
    }


    @Override
    public String toString() {
        return "BusinessProfileProductJsonType{" +
            "customerInfoDTO=" + customerInfoDTO +
            ", productDetails=" + productDetails +
            '}';
    }
}
