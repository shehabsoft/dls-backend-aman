package ae.rta.dls.backend.security.dto;

import ae.rta.dls.backend.domain.type.MultilingualJsonType;

import java.io.Serializable;

public class UserContextDTO implements Serializable {

    private String username;

    private Long userId;

    private String bpmUsername;

    private String userType;

    private MultilingualJsonType userTypeDescription;

    private String englishCustomerName;

    private String arabicCustomerName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBpmUsername() {
        return bpmUsername;
    }

    public void setBpmUsername(String bpmUsername) {
        this.bpmUsername = bpmUsername;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public MultilingualJsonType getUserTypeDescription() {
        return userTypeDescription;
    }

    public void setUserTypeDescription(MultilingualJsonType userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
    }

    public String getEnglishCustomerName() {
        return englishCustomerName;
    }

    public void setEnglishCustomerName(String englishCustomerName) {
        this.englishCustomerName = englishCustomerName;
    }

    public String getArabicCustomerName() {
        return arabicCustomerName;
    }

    public void setArabicCustomerName(String arabicCustomerName) {
        this.arabicCustomerName = arabicCustomerName;
    }

    @Override
    public String toString() {
        return "UserContextDTO{" +
            "username='" + username + '\'' +
            ", userId=" + userId +
            ", bpmUsername='" + bpmUsername + '\'' +
            ", userType='" + userType + '\'' +
            ", userTypeDescription=" + userTypeDescription +
            ", englishCustomerName='" + englishCustomerName + '\'' +
            ", ArabicCustomerName='" + arabicCustomerName + '\'' +
            '}';
    }
}
