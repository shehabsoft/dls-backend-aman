package ae.rta.dls.backend.domain.type;

import ae.rta.dls.backend.common.util.brm.calculation.fee.FeesListVM;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Fee Document Json Type.
 *
 * This type was developed to service document generic attributes for any service request.
 *
 * @author Mohammad Abulawi
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FeeDocumentJsonType implements Serializable {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("totalAmount")
    private Integer totalAmount;

    @JsonProperty("allFeesExempted")
    private Boolean allFeesExempted;

    @JsonProperty("feesList")
    private FeesListVM feesListVM;

    @JsonProperty("totalExemptedAmount")
    private Integer totalExemptedAmount;

    @JsonProperty("applicationRef")
    private Long applicationRef;

    @JsonProperty("serviceCode")
    private Long serviceCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getAllFeesExempted() {
        return allFeesExempted;
    }

    public void setAllFeesExempted(Boolean allFeesExempted) {
        this.allFeesExempted = allFeesExempted;
    }

    public FeesListVM getFeesListVM() {
        return feesListVM;
    }

    public void setFeesListVM(FeesListVM feesListVM) {
        this.feesListVM = feesListVM;
    }

    public Integer getTotalExemptedAmount() {
        return totalExemptedAmount;
    }

    public void setTotalExemptedAmount(Integer totalExemptedAmount) {
        this.totalExemptedAmount = totalExemptedAmount;
    }

    public Long getApplicationRef() {
        return applicationRef;
    }

    public void setApplicationRef(Long applicationRef) {
        this.applicationRef = applicationRef;
    }

    public Long getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Long serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "FeeDocumentJsonType{" +
            "message='" + message + '\'' +
            ", totalAmount=" + totalAmount +
            ", allFeesExempted=" + allFeesExempted +
            ", feesListVM=" + feesListVM +
            ", totalExemptedAmount=" + totalExemptedAmount +
            ", applicationRef=" + applicationRef +
            ", serviceCode=" + serviceCode +
            '}';
    }
}
