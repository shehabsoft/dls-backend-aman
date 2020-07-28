package ae.rta.dls.backend.domain.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Multilingual Json Type.
 *
 * This type was developed to define any multilingual attribute on the data layer.
 * This class is currently support 6 languages and it can be extended by adding
 * the required language code based on ISO 639-1 Code as an attribute here with
 * its related getter/setter.
 *
 * @author Mena Emiel.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MultilingualJsonType implements Serializable {

    /** Arabic language code */
    @JsonProperty("ar")
    @NotNull
    private String ar;

    /** English language code  */
    @JsonProperty("en")
    @NotNull
    private String en;

    /** French language code  */
    @JsonProperty("fr")
    private String fr;

    /** Urdu language code  */
    @JsonProperty("ur")
    private String ur;

    /** Hindi language code  */
    @JsonProperty("hi")
    private String hi;

    /** China language code  */
    @JsonProperty("zh")
    private String zh;

    /*
     * Default Constructor
     */
    public MultilingualJsonType() {
        this.ar = "";
        this.en = "";
    }

    /*
     * Constructor for all accessors
     */
    public MultilingualJsonType(String ar, String en, String fr, String ur, String hi, String zh) {
        this.ar = ar;
        this.en = en;
        this.fr = fr;
        this.ur = ur;
        this.hi = hi;
        this.zh = zh;
    }

    /*
     * Constructor for Arabic and English
     */
    public MultilingualJsonType(String ar, String en) {
        this.ar = ar;
        this.en = en;
        this.fr = "";
        this.ur = "";
        this.hi = "";
        this.zh = "";
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getUr() {
        return ur;
    }

    public void setUr(String ur) {
        this.ur = ur;
    }

    public String getHi() {
        return hi;
    }

    public void setHi(String hi) {
        this.hi = hi;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    @Override
    public String toString() {
        return "MultilingualJsonType{" +
                "ar=" + getAr() +
                ", en='" + getEn() + "'" +
                ", fr='" + getFr() + "'" +
                ", ur='" + getUr() + "'" +
                ", hi='" + getHi() + "'" +
                ", ur='" + getZh() + "'" +
                "}";
    }
}
