package ae.rta.dls.backend.web.rest.vm.ntf;

import ae.rta.dls.backend.common.util.DataFormatterUtil;
import ae.rta.dls.backend.domain.enumeration.common.Language;
import ae.rta.dls.backend.domain.enumeration.common.SystemType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * View Model object for storing the required information for send sms notification request.
 *
 * @author Rami Nassar
 */
@ApiModel(description = "Send Sms Notification Request VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SendSmsNotificationRequestVM implements Serializable {

    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @NotNull
    @JsonProperty("message")
    private String message;

    @NotNull
    @JsonProperty("messageSubject")
    private String messageSubject;

    @NotNull
    @JsonProperty("systemType")
    private SystemType systemType;

    @JsonProperty("language")
    private Language language;

    @NotBlank
    @Pattern(regexp = DataFormatterUtil.MOBILE_NUMBER_PATTERN)
    @Size(min = 12, max = 12)
    @JsonProperty("mobileNo")
    private String mobileNo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SystemType getSystemType() {
        return systemType;
    }

    public void setSystemType(SystemType systemType) {
        this.systemType = systemType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    @Override
    public String toString() {
        return "SendSmsNotificationRequestVM{" +
                "applicationId=" + applicationId +
                ", message='" + message + '\'' +
                ", messageSubject='" + messageSubject + '\'' +
                ", systemType=" + systemType +
                ", language=" + language +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }
}
