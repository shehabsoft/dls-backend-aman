package ae.rta.dls.backend.security.dto;

import java.io.Serializable;

public class ProfileContextDTO implements Serializable {

    private String channelCode;

    private String userRole;

    private UserContextDTO userContextDTO;

    private CorporateContextDTO corporateContextDTO;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public UserContextDTO getUserContextDTO() {
        return userContextDTO;
    }

    public void setUserContextDTO(UserContextDTO userContextDTO) {
        this.userContextDTO = userContextDTO;
    }

    public CorporateContextDTO getCorporateContextDTO() {
        return corporateContextDTO;
    }

    public void setCorporateContextDTO(CorporateContextDTO corporateContextDTO) {
        this.corporateContextDTO = corporateContextDTO;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "ProfileContextDTO{" +
            "channelCode='" + channelCode + '\'' +
            ", userContextDTO=" + userContextDTO +
            ", corporateContextDTO=" + corporateContextDTO +
            ", userRole=" + userRole +
            '}';
    }
}
