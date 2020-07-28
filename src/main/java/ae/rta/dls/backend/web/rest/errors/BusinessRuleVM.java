package ae.rta.dls.backend.web.rest.errors;

import java.io.Serializable;

public class BusinessRuleVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String code;

    private final String messageEn;

    private final String messageAr;

    public BusinessRuleVM(String code, String messageEn, String messageAr) {
        this.code = code;
        this.messageEn = messageEn;
        this.messageAr = messageAr;
    }

    public String getCode() {
        return code;
    }

    public String getMessageEn() {
        return messageEn;
    }

    public String getMessageAr() {
        return messageAr;
    }
}
