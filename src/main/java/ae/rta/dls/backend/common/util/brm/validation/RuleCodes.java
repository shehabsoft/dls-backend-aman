package ae.rta.dls.backend.common.util.brm.validation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RuleCodes implements Serializable {

    /*
     * Instance Variables
     */
    private List<String> list;
    private Boolean reqAllRuleExempted;

    /*
     * Constructor
     */
    public RuleCodes() {
        list = new ArrayList<>();
    }

    /*
     * Setters & Getters
     */
    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "RuleCodes{" +
            "list=" + list +
            ", reqAllRuleExempted=" + reqAllRuleExempted +
            '}';
    }
}
