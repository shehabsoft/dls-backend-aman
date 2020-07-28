package ae.rta.dls.backend.common.util.brm.calculation.fee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * Fee VM
 *
 * @author Tariq Abu Amireh
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FeeVM implements Serializable {

    /*
     * Bean Fields.
     */

    @JsonProperty("code")
    private String code;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("descriptionAr")
    private String descriptionAr;

    @JsonProperty("descriptionEn")
    private String descriptionEn;

    @JsonProperty("exempted")
    private Boolean exempted;

    @JsonProperty("free")
    private Boolean free;

    @JsonProperty("beneficiaryCode")
    private String beneficiaryCode;

    /*
     * Class Constructors.
     */

    /**
     * Fee Class Default Constructor.
     */
    public FeeVM() {
        this.code = "";
        this.amount = 0;
        this.descriptionAr = "";
        this.descriptionEn = "";
        this.exempted = false;
        this.free = false;
        this.beneficiaryCode = "";
    }

    /**
     * Fee Class Constructor.
     *
     * @param code Fee Code.
     * @param amount Fee Amount.
     * @param descriptionAr Fee Arabic Description.
     * @param descriptionEn Fee English Description.
     * @param exempted Fee Exempted Flag.
     * @param beneficiaryCode Beneficiary Code.
     */
    public FeeVM(String code, Integer amount, String descriptionAr, String descriptionEn, Boolean exempted, Boolean free, String beneficiaryCode) {
        this();
        this.code = code;
        this.amount = amount;
        this.descriptionAr = descriptionAr;
        this.descriptionEn = descriptionEn;
        this.exempted = exempted;
        this.free = free;
        this.beneficiaryCode = beneficiaryCode;
    }

    /*
     * Bean Fields Accessors.
     */

    /**
     * Get Fee Code.
     *
     * @return Fee Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Set Fee Code.
     *
     * @param code Fee Code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get Fee Amount.
     *
     * @return Fee Amount.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Set Fee Amount.
     *
     * @param amount Fee Amount.
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Get Fee Arabic Description.
     *
     * @return Fee Arabic Description.
     */
    public String getDescriptionAr() {
        return descriptionAr;
    }

    /**
     * Get Fee Arabic Description.
     *
     * @param descriptionAr Fee Arabic Description.
     */
    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    /**
     * Get Fee English Description.
     *
     * @return Fee English Description.
     */
    public String getDescriptionEn() {
        return descriptionEn;
    }

    /**
     * Set Fee English Description.
     *
     * @param descriptionEn Fee English Description.
     */
    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    /**
     * Get Fee Exempted Flag.
     *
     * @return Fee Exempted Flag.
     */
    public Boolean getExempted() {
        return exempted;
    }

    /**
     * Set Fee Exempted Flag.
     *
     * @param exempted Fee Exempted Flag.
     */
    public void setExempted(Boolean exempted) {
        this.exempted = exempted;
    }


    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    /**
     * Get Beneficiary Code.
     *
     * @return Beneficiary Code.
     */
    public String getBeneficiaryCode() {
        return beneficiaryCode;
    }

    /**
     * Set Beneficiary Code.
     *
     * @param beneficiaryCode Beneficiary Code.
     */
    public void setBeneficiaryCode(String beneficiaryCode) {
        this.beneficiaryCode = beneficiaryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeeVM feeVM = (FeeVM) o;
        if (feeVM.getCode() == null || getCode() == null) {
            return false;
        }
        return Objects.equals(getCode(), feeVM.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode());
    }

    @Override
    public String toString() {
        return "FeeVM{" +
            "code='" + code + '\'' +
            ", amount=" + amount +
            ", descriptionAr='" + descriptionAr + '\'' +
            ", descriptionEn='" + descriptionEn + '\'' +
            ", exempted=" + exempted +
            ", free=" + free +
            ", beneficiaryCode='" + beneficiaryCode + '\'' +
            '}';
    }
}

