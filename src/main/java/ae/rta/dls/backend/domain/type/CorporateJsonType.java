package ae.rta.dls.backend.domain.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * Corporate Json Type.
 *
 * @author Mohammad Abulawi.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CorporateJsonType implements Serializable {

    @JsonProperty("tradeLicenseNo")
    private String tradeLicenseNo;

    @JsonProperty("englishName")
    private String englishName;

    @JsonProperty("arabicName")
    private String arabicName;

    /*
     * Default Constructor
     */
    public CorporateJsonType() {
        // Default Constructor..
    }

    public CorporateJsonType(String tradeLicenseNo, String englishName,String arabicName) {
        this.tradeLicenseNo = tradeLicenseNo;
        this.englishName = englishName;
        this.arabicName = arabicName;
    }

    public String getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public void setTradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    @Override
    public String toString() {
        return "CorporateJsonType{" +
            "tradeLicenseNo='" + tradeLicenseNo + '\'' +
            ", englishName='" + englishName + '\'' +
            ", arabicName='" + arabicName + '\'' +
            '}';
    }
}
