package ae.rta.dls.backend.service.dto.prd;

import ae.rta.dls.backend.domain.enumeration.um.Gender;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Customer Info DTO
 *
 * @author Mohammad Qasim
 */
public class CustomerInfoDTO extends AbstractAuditingDTO {

    @JsonProperty("trafficCodeNo")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long trafficCodeNo;

    @JsonProperty("emiratesId")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private  String emiratesId;

    @JsonProperty("arabicFullName")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String arabicFullName;

    @JsonProperty("englishFullName")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String englishFullName;

    @JsonProperty("birthdate")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDate birthdate;

    @JsonProperty("gender")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Gender gender;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("nationality")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private NationalityDTO nationality;

    @JsonProperty("paymentMethod")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String paymentMethod;

    @JsonProperty("paymentReference")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long paymentReference;

    @JsonProperty("paymentDate")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDateTime paymentDate;

    public Long getTrafficCodeNo() {
        return trafficCodeNo;
    }

    public void setTrafficCodeNo(Long trafficCodeNo) {
        this.trafficCodeNo = trafficCodeNo;
    }

    public String getEmiratesId() {
        return emiratesId;
    }

    public void setEmiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
    }

    public String getArabicFullName() {
        return arabicFullName;
    }

    public void setArabicFullName(String arabicFullName) {
        this.arabicFullName = arabicFullName;
    }

    public String getEnglishFullName() {
        return englishFullName;
    }

    public void setEnglishFullName(String englishFullName) {
        this.englishFullName = englishFullName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public NationalityDTO getNationality() {
        return nationality;
    }

    public void setNationality(NationalityDTO nationality) {
        this.nationality = nationality;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }


    @Override
    public String toString() {
        return "CustomerInfoDTO{" +
            "trafficCodeNo=" + trafficCodeNo +
            ", emiratesId='" + emiratesId + '\'' +
            ", arabicFullName='" + arabicFullName + '\'' +
            ", englishFullName='" + englishFullName + '\'' +
            ", birthdate=" + birthdate +
            ", gender=" + gender +
            ", photo='" + photo + '\'' +
            ", nationality=" + nationality +
            ", paymentMethod='" + paymentMethod + '\'' +
            ", paymentReference=" + paymentReference +
            ", paymentDate=" + paymentDate +
            '}';
    }
}
