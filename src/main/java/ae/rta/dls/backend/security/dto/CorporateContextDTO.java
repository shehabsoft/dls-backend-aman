package ae.rta.dls.backend.security.dto;

import ae.rta.dls.backend.domain.type.MultilingualJsonType;

import java.io.Serializable;

public class CorporateContextDTO implements Serializable {

    private String tradeLicenseNo;

    private String agentName;

    private String englishCorporateName;

    private String arabicCorporateName;

    private String corporateType;

    private MultilingualJsonType corporateTypeDescription;


    public String getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public void setTradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getEnglishCorporateName() {
        return englishCorporateName;
    }

    public void setEnglishCorporateName(String englishCorporateName) {
        this.englishCorporateName = englishCorporateName;
    }

    public String getArabicCorporateName() {
        return arabicCorporateName;
    }

    public void setArabicCorporateName(String arabicCorporateName) {
        this.arabicCorporateName = arabicCorporateName;
    }

    public String getCorporateType() {
        return corporateType;
    }

    public void setCorporateType(String corporateType) {
        this.corporateType = corporateType;
    }

    public MultilingualJsonType getCorporateTypeDescription() {
        return corporateTypeDescription;
    }

    public void setCorporateTypeDescription(MultilingualJsonType corporateTypeDescription) {
        this.corporateTypeDescription = corporateTypeDescription;
    }

    @Override
    public String toString() {
        return "CorporateContextDTO{" +
            "tradeLicenseNo='" + tradeLicenseNo + '\'' +
            ", agentName='" + agentName + '\'' +
            ", englishCorporateName='" + englishCorporateName + '\'' +
            ", arabicCorporateName='" + arabicCorporateName + '\'' +
            ", corporateType='" + corporateType + '\'' +
            ", corporateTypeDescription=" + corporateTypeDescription +
            '}';
    }
}
