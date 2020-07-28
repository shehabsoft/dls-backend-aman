package ae.rta.dls.backend.common.util.brm.calculation.fee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Fee Calculation Request
 *
 * @author Tariq Abu Amireh
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FeeCalculationRequest implements Serializable {

    /*
     * Instance Variables
     */
    @JsonProperty("handicapped")
    private Boolean handicapped;

    @JsonProperty("serviceCode")
    private Long serviceCode;

    @JsonProperty("serviceFeeCriteria")
    private ServiceFeeCriteriaVM serviceFeeCriteriaVM;

    @JsonProperty("exemptedFees")
    private ExemptedFeesVM exemptedFees;

    @JsonProperty("exemptAllFees")
    private Boolean exemptAllFees;

    @JsonProperty("applicationRef")
    private Long applicationRef;

    @JsonProperty("tcnFeesCollected")
    private Boolean tcnFeesCollected;

    /*
     * Default Constructor
     */
    public FeeCalculationRequest() {
        // Empty Constructor
    }

    /*
     * Setters & Getters
     */

    public Long getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(Long serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Boolean getHandicapped() {
        return handicapped;
    }

    public void setHandicapped(Boolean handicapped) {
        this.handicapped = handicapped;
    }

    public ServiceFeeCriteriaVM getServiceFeeCriteriaVM() {
        return serviceFeeCriteriaVM;
    }

    public void setServiceFeeCriteriaVM(ServiceFeeCriteriaVM serviceFeeCriteriaVM) {
        this.serviceFeeCriteriaVM = serviceFeeCriteriaVM;
    }

    public ExemptedFeesVM getExemptedFees() {
        return exemptedFees;
    }

    public void setExemptedFees(ExemptedFeesVM exemptedFees) {
        this.exemptedFees = exemptedFees;
    }

    public Boolean getExemptAllFees() {
        return exemptAllFees;
    }

    public void setExemptAllFees(Boolean exemptAllFees) {
        this.exemptAllFees = exemptAllFees;
    }

    public Long getApplicationRef() {
        return applicationRef;
    }

    public void setApplicationRef(Long applicationRef) {
        this.applicationRef = applicationRef;
    }

    public Boolean getTcnFeesCollected() {
        return tcnFeesCollected;
    }

    public void setTcnFeesCollected(Boolean tcnFeesCollected) {
        this.tcnFeesCollected = tcnFeesCollected;
    }

    @Override
    public String toString() {
        return "FeeCalculationRequest{" +
            "handicapped=" + handicapped +
            ", serviceCode=" + serviceCode +
            ", serviceFeeCriteriaVM=" + serviceFeeCriteriaVM +
            ", exemptedFees=" + exemptedFees +
            ", exemptAllFees=" + exemptAllFees +
            ", applicationRef=" + applicationRef +
            ", tcnFeesCollected=" + tcnFeesCollected +
            '}';
    }
}
