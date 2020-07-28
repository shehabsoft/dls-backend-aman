package ae.rta.dls.backend.common.util.brm.validation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RuleValidationResponse implements Serializable {

    /*
     * Instance Variables
     */
    @JsonProperty("message")
    private String message;

    @JsonProperty("hasErrorRules")
    private boolean hasErrorRules;

    @JsonProperty("hasWarningRules")
    private boolean hasWarningRules;

    @JsonProperty("allRulesExempted")
    private boolean allRulesExempted;

    @JsonProperty("allRulesExempted")
    private boolean hasInfoRules;

    @JsonProperty("rules")
    private Rules rules;

    @JsonProperty("applicationRef")
    private Long applicationRef;


    /*
     * Default Constructor
     */
    public RuleValidationResponse() { }


    /*
     * Constructor
     */
    public RuleValidationResponse(boolean hasErrorRules,
                                  boolean hasWarningRules,
                                  boolean allRulesExempted,
                                  boolean hasInfoRules,
                                  Rules rules) {

        this.hasErrorRules = hasErrorRules;
        this.hasWarningRules = hasWarningRules;
        this.allRulesExempted = allRulesExempted;
        this.hasInfoRules = hasInfoRules;
        this.rules = rules;
    }

    /*
     * Setters & Getters
     */
    public boolean isHasErrorRules() {
        return hasErrorRules;
    }

    public void setHasErrorRules(boolean hasErrorRules) {
        this.hasErrorRules = hasErrorRules;
    }

    public boolean isHasWarningRules() {
        return hasWarningRules;
    }

    public void setHasWarningRules(boolean hasWarningRules) {
        this.hasWarningRules = hasWarningRules;
    }

    public boolean isAllRulesExempted() {
        return allRulesExempted;
    }

    public void setAllRulesExempted(boolean allRulesExempted) {
        this.allRulesExempted = allRulesExempted;
    }

    public boolean isHasInfoRules() {
        return hasInfoRules;
    }

    public void setHasInfoRules(boolean hasInfoRules) {
        this.hasInfoRules = hasInfoRules;
    }

    public Rules getRules() {
        return rules;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }

    public Long getApplicationRef() {
        return applicationRef;
    }

    public void setApplicationRef(Long applicationRef) {
        this.applicationRef = applicationRef;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {

        return "RuleValidationResponse[hasErrorRules:"+hasErrorRules+"\n"
                +  "hasWarningRules:"+hasWarningRules+"\n"
                +  "allRulesExempted:"+allRulesExempted+"\n"
                +  "hasInfoRules:"+hasInfoRules+"\n"
                +  "applicationRef:"+ applicationRef +"\n"
                +  "message:"+message+"\n"
                +  "rules:"+rules.toString()+"]";
    }

}
