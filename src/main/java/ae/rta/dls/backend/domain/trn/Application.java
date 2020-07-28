package ae.rta.dls.backend.domain.trn;

import ae.rta.dls.backend.domain.*;
import ae.rta.dls.backend.domain.sct.ApplicationType;
import ae.rta.dls.backend.domain.util.*;
import ae.rta.dls.backend.domain.type.ApplicationCriteriaJsonType;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import ae.rta.dls.backend.domain.enumeration.trn.ApplicationStatus;
import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;
import org.hibernate.annotations.Cache;

/**
 * Application (trn_application) entity.
 * @author Mena Emiel.
 */
@Entity
@Table(name = "trn_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Application extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "applSequenceGenerator")
    @SequenceGenerator(name = "applSequenceGenerator", sequenceName = "appl_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;

    @Column(name = "status_description", nullable = false)
    //@Convert(converter = MultilingualJsonConverter.class)
    @Type(type = "json")
    private MultilingualJsonType statusDescription;

    @NotNull
    @Column(name = "status_date", nullable = false)
    private LocalDateTime statusDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "active_phase", nullable = false)
    private PhaseType activePhase;

    @Column(name = "confirmed_by")
    private String confirmedBy;

    @Column(name = "confirmation_date")
    private LocalDateTime confirmationDate;

    @Column(name = "rejected_by")
    private String rejectedBy;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "rejection_date")
    private LocalDateTime rejectionDate;

    @Column(name = "process_instance_id", unique = true)
    private Long processInstanceId;

    @NotNull
    @Column(name = "channel_code", nullable = false)
    private String channelCode;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "english_customer_name")
    private String englishCustomerName;

    @Column(name = "arabic_customer_name")
    private String arabicCustomerName;

    @Column(name = "trade_license_no")
    private String tradeLicenseNo;

    @Column(name = "english_corporate_name")
    private String englishCorporateName;

    @Column(name = "arabic_corporate_name")
    private String arabicCorporateName;

    @NotNull
    @Column(name = "user_type", nullable = false)
    private String userType;

    @Column(name = "user_type_description", nullable = false)
    //@Convert(converter = MultilingualJsonConverter.class)
    @Type(type = "json")
    private MultilingualJsonType userTypeDescription;

    @NotNull
    @Column(name = "user_role", nullable = false)
    private String userRole;

    //@Lob
    @Column(name = "application_criteria")
    @Type(type = "json")
    //@Convert(converter = ApplicationCriteriaJsonConverter.class)
    private ApplicationCriteriaJsonType applicationCriteria;

    @OneToMany(mappedBy = "application" ,orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationPhase> applicationPhases = new HashSet<>();

    @OneToMany(mappedBy = "application" ,orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceRequest> serviceRequests = new HashSet<>();

    @OneToMany(mappedBy = "application" ,orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationViolation> applicationViolations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("applications")
    private ApplicationType applicationType;

    @Column(name = "persona")
    private String persona;

    @Column(name = "synched_entity_id", unique = true)
    private Long synchedEntityId;

    @Column(name = "persona_version")
    private String personaVersion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public Application status(ApplicationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public MultilingualJsonType getStatusDescription() {
        return statusDescription;
    }

    public Application statusDescription(MultilingualJsonType statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }

    public void setStatusDescription(MultilingualJsonType statusDescription) {
        this.statusDescription = statusDescription;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public Application statusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    public PhaseType getActivePhase() {
        return activePhase;
    }

    public Application activePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
        return this;
    }

    public void setActivePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public Application confirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
        return this;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public LocalDateTime getConfirmationDate() {
        return confirmationDate;
    }

    public Application confirmationDate(LocalDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
        return this;
    }

    public void setConfirmationDate(LocalDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getRejectedBy() {
        return rejectedBy;
    }

    public Application rejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
        return this;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public Application rejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
        return this;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getRejectionDate() {
        return rejectionDate;
    }

    public Application rejectionDate(LocalDateTime rejectionDate) {
        this.rejectionDate = rejectionDate;
        return this;
    }

    public void setRejectionDate(LocalDateTime rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public Application processInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public Application channelCode(String channelCode) {
        this.channelCode = channelCode;
        return this;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }


    public Long getUserId() {
        return userId;
    }

    public Application userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEnglishCustomerName() {
        return englishCustomerName;
    }

    public Application englishCustomerName(String englishCustomerName) {
        this.englishCustomerName = englishCustomerName;
        return this;
    }

    public void setEnglishCustomerName(String englishCustomerName) {
        this.englishCustomerName = englishCustomerName;
    }

    public String getArabicCustomerName() {
        return arabicCustomerName;
    }

    public Application arabicCustomerName(String arabicCustomerName) {
        this.arabicCustomerName = arabicCustomerName;
        return this;
    }

    public void setArabicCustomerName(String arabicCustomerName) {
        this.arabicCustomerName = arabicCustomerName;
    }

    public String getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public Application tradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
        return this;
    }

    public void setTradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
    }

    public String getEnglishCorporateName() {
        return englishCorporateName;
    }

    public Application englishCorporateName(String englishCorporateName) {
        this.englishCorporateName = englishCorporateName;
        return this;
    }

    public void setEnglishCorporateName(String englishCorporateName) {
        this.englishCorporateName = englishCorporateName;
    }

    public String getArabicCorporateName() {
        return arabicCorporateName;
    }

    public Application arabicCorporateName(String arabicCorporateName) {
        this.arabicCorporateName = arabicCorporateName;
        return this;
    }

    public void setArabicCorporateName(String arabicCorporateName) {
        this.arabicCorporateName = arabicCorporateName;
    }

    public String getUserType() {
        return userType;
    }

    public Application userType(String userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public MultilingualJsonType getUserTypeDescription() {
        return userTypeDescription;
    }

    public Application userTypeDescription(MultilingualJsonType userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
        return this;
    }

    public void setUserTypeDescription(MultilingualJsonType userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
    }

    public String getUserRole() {
        return userRole;
    }

    public Application userRole(String userRole) {
        this.userRole = userRole;
        return this;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public ApplicationCriteriaJsonType getApplicationCriteria() {
        return applicationCriteria;
    }

    public Application applicationCriteria(ApplicationCriteriaJsonType applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
        return this;
    }

    public void setApplicationCriteria(ApplicationCriteriaJsonType applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
    }

    public Set<ApplicationPhase> getApplicationPhases() {
        return applicationPhases;
    }

    public Application applicationPhases(Set<ApplicationPhase> applicationPhases) {
        this.applicationPhases = applicationPhases;
        return this;
    }

    public Application addApplicationPhase(ApplicationPhase applicationPhase) {
        this.applicationPhases.add(applicationPhase);
        applicationPhase.setApplication(this);
        return this;
    }

    public Application removeApplicationPhase(ApplicationPhase applicationPhase) {
        this.applicationPhases.remove(applicationPhase);
        applicationPhase.setApplication(null);
        return this;
    }

    public void setApplicationPhases(Set<ApplicationPhase> applicationPhases) {
        this.applicationPhases = applicationPhases;
    }

    public Set<ServiceRequest> getServiceRequests() {
        return serviceRequests;
    }

    public Application serviceRequests(Set<ServiceRequest> serviceRequests) {
        this.serviceRequests = serviceRequests;
        return this;
    }

    public Application addServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequests.add(serviceRequest);
        serviceRequest.setApplication(this);
        return this;
    }

    public Application removeServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequests.remove(serviceRequest);
        serviceRequest.setApplication(null);
        return this;
    }

    public void setServiceRequests(Set<ServiceRequest> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public Set<ApplicationViolation> getApplicationViolations() {
        return applicationViolations;
    }

    public Application applicationViolations(Set<ApplicationViolation> applicationViolations) {
        this.applicationViolations = applicationViolations;
        return this;
    }

    public Application addApplicationViolation(ApplicationViolation applicationViolation) {
        this.applicationViolations.add(applicationViolation);
        applicationViolation.setApplication(this);
        return this;
    }

    public Application removeApplicationViolation(ApplicationViolation applicationViolation) {
        this.applicationViolations.remove(applicationViolation);
        applicationViolation.setApplication(null);
        return this;
    }

    public void setApplicationViolations(Set<ApplicationViolation> applicationViolations) {
        this.applicationViolations = applicationViolations;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public Application applicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
        return this;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public Application persona(String persona) {
        this.persona = persona;
        return this;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public Long getSynchedEntityId() {
        return synchedEntityId;
    }

    public Application synchedEntityId(Long synchedEntityId) {
        this.synchedEntityId = synchedEntityId;
        return this;
    }

    public void setSynchedEntityId(Long synchedEntityId) {
        this.synchedEntityId = synchedEntityId;
    }

    public Application personaVersion(String personaVersion) {
        this.personaVersion = personaVersion;
        return this;
    }

    public String getPersonaVersion() {
        return personaVersion;
    }

    public void setPersonaVersion(String personaVersion) {
        this.personaVersion = personaVersion;
    }

// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Application application = (Application) o;
        if (application.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), application.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", statusDescription='" + getStatusDescription() + "'" +
            ", statusDate='" + getStatusDate() + "'" +
            ", activePhase='" + getActivePhase() + "'" +
            ", confirmedBy='" + getConfirmedBy() + "'" +
            ", confirmationDate='" + getConfirmationDate() + "'" +
            ", rejectedBy='" + getRejectedBy() + "'" +
            ", rejectionReason='" + getRejectionReason() + "'" +
            ", rejectionDate='" + getRejectionDate() + "'" +
            ", processInstanceId=" + getProcessInstanceId() +
            ", channelCode='" + getChannelCode() + "'" +
            ", userId='" + getUserId() + "'" +
            ", englishCustomerName='" + getEnglishCustomerName() + "'" +
            ", arabicCustomerName='" + getArabicCustomerName() + "'" +
            ", tradeLicenseNo='" + getTradeLicenseNo() + "'" +
            ", englishCorporateName='" + getEnglishCorporateName() + "'" +
            ", arabicCorporateName='" + getArabicCorporateName() + "'" +
            ", userType='" + getUserType() + "'" +
            ", userTypeDescription='" + getUserTypeDescription() + "'" +
            ", userRole='" + getUserRole() + "'" +
            ", applicationCriteria='" + getApplicationCriteria() + "'" +
            ", persona='" + getPersona() + "'" +
            ", personaVersion='" + getPersonaVersion() + "'" +
            "}";
    }
}
