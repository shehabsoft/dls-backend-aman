package ae.rta.dls.backend.service.trn.impl;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.trn.ApplicationStatus;
import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.security.util.SecurityUtils;
import ae.rta.dls.backend.security.dto.ProfileContextDTO;
import ae.rta.dls.backend.service.dto.trn.ApplicationPhaseDTO;
import ae.rta.dls.backend.service.trn.ApplicationPhaseService;
import ae.rta.dls.backend.service.trn.ApplicationService;
import ae.rta.dls.backend.domain.trn.Application;
import ae.rta.dls.backend.repository.trn.ApplicationRepository;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.mapper.trn.ApplicationMapper;
import ae.rta.dls.backend.web.rest.errors.NullValueException;
import ae.rta.dls.backend.web.rest.vm.trn.ProcessInstanceAppInfoRequestVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Application.
 */
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    private final ApplicationRepository applicationRepository;

    private final ApplicationMapper applicationMapper;

    private final SecurityUtils securityUtils;

    private final ApplicationPhaseService applicationPhaseService;

    private final CommonUtil commonUtil;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper, SecurityUtils securityUtils, ApplicationPhaseService applicationPhaseService, CommonUtil commonUtil) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
        this.securityUtils = securityUtils;
        this.applicationPhaseService = applicationPhaseService;
        this.commonUtil = commonUtil;
    }

    /*
     * Validate create application.
     *
     * @param applicationDTO
     */
    private void validateCreateApplication(ApplicationDTO applicationDTO) {

        if (applicationDTO == null) {
            throw new SystemException("applicationDTO cannot be null");
        }

        if (applicationDTO.getApplicationTypeId() == null) {
            throw new SystemException("application type ID cannot be null");
        }

        if (applicationDTO.getApplicationCriteria() == null) {
            throw new SystemException("application criteria cannot be null");
        }

        if (applicationDTO.getId() != null) {
            throw new SystemException("application ID must not be provided");
        }

        if (applicationDTO.getStatus() != null) {
            throw new SystemException("application status must not be provided");
        }

        if (applicationDTO.getStatusDate() != null) {
            throw new SystemException("application status date must not be provided");
        }

        if (applicationDTO.getStatusDescription() != null) {
            throw new SystemException("application status description must not be provided");
        }

        if (applicationDTO.getChannelCode() != null) {
            throw new SystemException("application channel code must not be provided");
        }

        if (applicationDTO.getUserId() != null) {
            throw new SystemException("application customer ID must not be provided");
        }

        if (applicationDTO.getArabicCustomerName() != null) {
            throw new SystemException("application arabic customer name must not be provided");
        }

        if (applicationDTO.getEnglishCustomerName() != null) {
            throw new SystemException("application english customer name must not be provided");
        }

        if (applicationDTO.getActivePhase() != null) {
            throw new SystemException("application active phase must not be provided");
        }

        if (applicationDTO.getProcessInstanceId() != null) {
            throw new SystemException("application process instance id must not be provided");
        }

        checkCorporateInfo(applicationDTO);
        checkNextPhaseInfo(applicationDTO);
        checkUserInfo(applicationDTO);
    }

    /**
     * Check Corporate info
     *
     * @param applicationDTO Application DTO
     */
    private void checkCorporateInfo(ApplicationDTO applicationDTO) {

        if (applicationDTO.getTradeLicenseNo() != null) {
            throw new SystemException("application trade license number must not be provided");
        }

        if (applicationDTO.getArabicCorporateName() != null) {
            throw new SystemException("application arabic corporate name must not be provided");
        }

        if (applicationDTO.getEnglishCorporateName() != null) {
            throw new SystemException("application english corporate name must not be provided");
        }

    }

    /**
     * Check user info
     *
     * @param applicationDTO Application DTO
     */
    private void checkUserInfo(ApplicationDTO applicationDTO) {

        if (applicationDTO.getUserRole() != null) {
            throw new SystemException("application user role must not be provided");
        }

        if (applicationDTO.getUserType() != null) {
            throw new SystemException("application user type must not be provided");
        }

        if (applicationDTO.getUserTypeDescription() != null) {
            throw new SystemException("application user type description must not be provided");
        }

    }

    /**
     * Check Next Phase info
     *
     * @param applicationDTO Application DTO
     */
    private void checkNextPhaseInfo(ApplicationDTO applicationDTO) {

        if (applicationDTO.getConfirmedBy() != null) {
            throw new SystemException("application confirmed by must not be provided");
        }

        if (applicationDTO.getConfirmationDate() != null) {
            throw new SystemException("application confirmation date must not be provided");
        }

        if (applicationDTO.getRejectedBy() != null) {
            throw new SystemException("application rejected by must not be provided");
        }

        if (applicationDTO.getRejectionDate() != null) {
            throw new SystemException("application rejection date must not be provided");
        }

        if (applicationDTO.getRejectionReason() != null) {
            throw new SystemException("application rejection reason must not be provided");
        }

        if (applicationDTO.getServiceRequests() != null && !applicationDTO.getServiceRequests().isEmpty()) {
            throw new SystemException("application service requests must not be provided");
        }
    }

    /**
     * Validate Update Application.
     *
     * @param applicationDTO
     */
    private void validateUpdateApplication(ApplicationDTO applicationDTO) {

        if (applicationDTO == null) {
            throw new SystemException("applicationDTO cannot be null");
        }

        if (applicationDTO.getApplicationTypeId() == null) {
            throw new NullValueException("application type ID");
        }

        if (applicationDTO.getId() == null) {
            throw new NullValueException("application ID");
        }

        if (applicationDTO.getStatus() == null) {
            throw new NullValueException("application status");
        }

        if (applicationDTO.getStatusDate() == null) {
            throw new NullValueException("application status date");
        }

        if (applicationDTO.getStatusDescription() == null) {
            throw new NullValueException("application status description");
        }

        if (applicationDTO.getUserRole() == null) {
            throw new NullValueException("application user role");
        }

        if (applicationDTO.getUserType() == null) {
            throw new NullValueException("application user type");
        }

        if (applicationDTO.getUserTypeDescription() == null) {
            throw new NullValueException("application user type description");
        }

        if (applicationDTO.getChannelCode() == null) {
            throw new NullValueException("application channel code");
        }

        if (applicationDTO.getActivePhase() == null) {
            throw new NullValueException("application active phase");
        }

        if (applicationDTO.getApplicationCriteria() == null) {
            throw new NullValueException("application criteria");
        }
    }

    /**
     * Save a application.
     *
     * @param applicationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ApplicationDTO update(ApplicationDTO applicationDTO) {

        Optional<ApplicationDTO>  oldApplicationDTO =  findOne(applicationDTO.getId());
        if(oldApplicationDTO.isPresent()) {
            applicationDTO = commonUtil.copyNotNullProperty(oldApplicationDTO.get(), applicationDTO);
        }

        validateUpdateApplication(applicationDTO);

        /* ******************************************************************* */
        /* ********************* Update Application Phase ******************** */
        /* ******************************************************************* */

        LocalDateTime now = LocalDateTime.now();
        Optional<String> user = SecurityUtils.getCurrentUserLogin();

        if(!user.isPresent() || !oldApplicationDTO.isPresent()) {
            return null;
        }

        if(!oldApplicationDTO.get().getActivePhase().value().equalsIgnoreCase(applicationDTO.getActivePhase().value())) {

            if(applicationDTO.getActivePhase().equals(PhaseType.PRINT_LICENSE)) {
                applicationDTO.setStatus(ApplicationStatus.COMPLETED);
                applicationDTO.setConfirmationDate(now);
                applicationDTO.setStatusDate(now);
                applicationDTO.setConfirmedBy(user.get());
            } else if(applicationDTO.getActivePhase().equals(PhaseType.APPLICATION_REJECTION)) {
                applicationDTO.setStatus(ApplicationStatus.REJECTED);
                applicationDTO.setRejectionDate(now);
                applicationDTO.setStatusDate(now);
                applicationDTO.setRejectedBy(user.get());
                applicationDTO.setRejectionReason("Rejected by driving institute user");
            }
        }

        if(applicationDTO.getStatus() != null) {
            MultilingualJsonType appStatusDesc = commonUtil.getDomainValueDescription(applicationDTO.getStatus().value(), ApplicationStatus.DOMAIN_CODE);

            if (appStatusDesc == null) {
                throw new NullValueException("application status description");
            }
            applicationDTO.setStatusDescription(appStatusDesc);
        }

        Application application = applicationMapper.toEntity(applicationDTO);
        application = applicationRepository.save(application);

        if(!oldApplicationDTO.get().getActivePhase().value().equalsIgnoreCase(applicationDTO.getActivePhase().value())) {
            updatePhase(application.getId(), application.getActivePhase());
        }

        return applicationMapper.toDto(application);
    }

    /**
     * Update Application Phase
     *
     * @param id : Application Id
     * @param phase : Active Phase
     *
     * @return the persisted entity
     */
    public ApplicationDTO updatePhase(Long id, PhaseType phase) {

        LocalDateTime now = LocalDateTime.now();

        /* ********************************************************* */
        /* ****************** Get Application By Id **************** */
        /* ********************************************************* */
        Optional<ApplicationDTO> applicationDTO = findOne(id);

        if(!applicationDTO.isPresent()) {
            return null;
        }

        /* ********************************************************* */
        /* ***************** Close Current Phase ******************* */
        /* ********************************************************* */
        ApplicationPhaseDTO applicationPhaseDTO =
                applicationPhaseService.getFirstByApplicationIdOrderByPhaseSequenceDesc(id);

        applicationPhaseDTO.setEndDate(now);
        applicationPhaseService.save(applicationPhaseDTO);

        /* ********************************************************* */
        /* ********************* Open Next Phase ******************* */
        /* ********************************************************* */
        ApplicationPhaseDTO newApplicationPhaseDTO = new ApplicationPhaseDTO();
        newApplicationPhaseDTO.setApplicationId(id);
        newApplicationPhaseDTO.setPhaseType(phase);
        newApplicationPhaseDTO.setStartDate(now);
        newApplicationPhaseDTO.setPhaseSequence(applicationPhaseDTO.getPhaseSequence()+1);
        newApplicationPhaseDTO.setPersona(applicationPhaseDTO.getPersona());

        if(phase.equals(PhaseType.PRINT_LICENSE) || phase.equals(PhaseType.APPLICATION_REJECTION)) {
            newApplicationPhaseDTO.setEndDate(now);
        }

        applicationPhaseService.save(newApplicationPhaseDTO);

        return applicationDTO.get();
    }

    /**
     * Save a application.
     *
     * @param applicationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ApplicationDTO create(ApplicationDTO applicationDTO) {

        log.debug("Request to save Application : {}", applicationDTO);

        validateCreateApplication(applicationDTO);

        ProfileContextDTO profile = securityUtils.getUserProfile();

        if (profile == null) {
            throw new NullValueException("user profile");
        }

        MultilingualJsonType appStatusDesc = commonUtil.getDomainValueDescription(ApplicationStatus.DRAFT.value(), ApplicationStatus.DOMAIN_CODE);

        if (appStatusDesc == null) {
            throw new NullValueException("application status description");
        }

        applicationDTO.setStatus(ApplicationStatus.DRAFT);
        applicationDTO.setStatusDescription(appStatusDesc);
        applicationDTO.setStatusDate(LocalDateTime.now());
        applicationDTO.setActivePhase(PhaseType.CUSTOMER_ELIGIBILITY);
        applicationDTO.setUserRole(profile.getUserRole());
        applicationDTO.setChannelCode(profile.getChannelCode());

        if (securityUtils.isPublicUser()) {
            applicationDTO.setUserId(profile.getUserContextDTO().getUserId());
            applicationDTO.setEnglishCustomerName(profile.getUserContextDTO().getEnglishCustomerName());
            applicationDTO.setArabicCustomerName(profile.getUserContextDTO().getArabicCustomerName());
            applicationDTO.setUserType(profile.getUserContextDTO().getUserType());
            applicationDTO.setUserTypeDescription(profile.getUserContextDTO().getUserTypeDescription());

        } else if(securityUtils.isCorporateUser()) {
            applicationDTO.setTradeLicenseNo(profile.getCorporateContextDTO().getTradeLicenseNo());
            applicationDTO.setEnglishCorporateName(profile.getCorporateContextDTO().getEnglishCorporateName());
            applicationDTO.setArabicCorporateName(profile.getCorporateContextDTO().getArabicCorporateName());
            applicationDTO.setUserType(profile.getCorporateContextDTO().getCorporateType());
            applicationDTO.setUserTypeDescription(profile.getCorporateContextDTO().getCorporateTypeDescription());
        }

        Application application = applicationMapper.toEntity(applicationDTO);
        application = applicationRepository.save(application);

        ApplicationPhaseDTO applicationPhaseDTO = new ApplicationPhaseDTO();
        applicationPhaseDTO.setPhaseType(PhaseType.CUSTOMER_ELIGIBILITY);
        applicationPhaseDTO.setPhaseSequence(1);
        applicationPhaseDTO.setStartDate(LocalDateTime.now());
        applicationPhaseDTO.setApplicationId(application.getId());

        applicationPhaseService.save(applicationPhaseDTO);

        return applicationMapper.toDto(application);
    }

    /**
     * Get all the applications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Applications");
        return applicationRepository.findAll(pageable)
            .map(applicationMapper::toDto);
    }

    /**
     * Get one application by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationDTO> findOne(Long id) {
        log.debug("Request to get Application : {}", id);
        return applicationRepository.findById(id)
            .map(applicationMapper::toDto);
    }

    /**
     * GET  /applications/processInstance/:processInstanceId : get the "processInstanceId" application.
     *
     * @param processInstanceId the Process instance id of the applicationDTO to retrieve
     * @return the entity
     */
    public Optional<ApplicationDTO> getApplicationByProcessInstanceId(Long processInstanceId) {
        log.debug("Request to get Application by processInstanceId : {}", processInstanceId);
        Optional<ApplicationDTO> applicationDTO = applicationRepository.findByProcessInstanceId(processInstanceId)
                                                                       .map(applicationMapper::toDto);

        if (applicationDTO.isPresent() &&
            applicationDTO.get().getActivePhase() != null &&
            !StringUtil.isBlank(applicationDTO.get().getActivePhase().value())) {

            MultilingualJsonType activePhaseDescription = commonUtil.getDomainValueDescription(applicationDTO.get().getActivePhase().value() , PhaseType.DOMAIN_CODE);
            applicationDTO.get().setActivePhaseDescription(activePhaseDescription);
        }

        return applicationDTO;
    }

    /**
     * Get process instance application by information
     *
     * @param processInstanceAppInfoRequestVM the Process instance application info
     * @return the application entity
     */
    public Optional<ApplicationDTO> getProcessInstanceApplicationByInfo(ProcessInstanceAppInfoRequestVM processInstanceAppInfoRequestVM) {
        log.debug("Request to get Application by processInstanceAppInfoRequestVM : {}", processInstanceAppInfoRequestVM);

        if (processInstanceAppInfoRequestVM == null || processInstanceAppInfoRequestVM.getProcessInstanceId() == null) {
            throw new NullValueException("Process instance id is null");
        }

        String status = processInstanceAppInfoRequestVM.getStatus() != null ? processInstanceAppInfoRequestVM.getStatus().value() : "";
        String emiratesId = processInstanceAppInfoRequestVM.getEmiratesId() != null ? processInstanceAppInfoRequestVM.getEmiratesId().trim() : "";
        String mobileNo = processInstanceAppInfoRequestVM.getMobileNo() != null ? processInstanceAppInfoRequestVM.getMobileNo().trim() : "";
        String licenseCategoryCode = processInstanceAppInfoRequestVM.getLicenseCategoryCode() != null ? processInstanceAppInfoRequestVM.getLicenseCategoryCode().trim() : "";

        // Find One By Info..
        Optional<ApplicationDTO> applicationDTO = applicationRepository.
                findOneByProcessInstanceInfo(processInstanceAppInfoRequestVM.getProcessInstanceId(), status, emiratesId, mobileNo,licenseCategoryCode,processInstanceAppInfoRequestVM.getApplicationReferenceNo())
                .map(applicationMapper::toDto);

        if (!applicationDTO.isPresent()) {
            return applicationDTO;
        }

        if(processInstanceAppInfoRequestVM.getApplicationDateFrom() != null
                && applicationDTO.get().getCreatedDate().toLocalDate().isBefore(processInstanceAppInfoRequestVM.getApplicationDateFrom())) {
            return Optional.empty();
        }

        if(processInstanceAppInfoRequestVM.getApplicationDateTo() != null
                && applicationDTO.get().getCreatedDate().toLocalDate().isAfter(processInstanceAppInfoRequestVM.getApplicationDateTo())) {
            return Optional.empty();
        }

        if (applicationDTO.get().getActivePhase() != null
                && !StringUtil.isBlank(applicationDTO.get().getActivePhase().value())) {

            MultilingualJsonType activePhaseDescription = commonUtil.getDomainValueDescription(applicationDTO.get().getActivePhase().value() , PhaseType.DOMAIN_CODE);
            applicationDTO.get().setActivePhaseDescription(activePhaseDescription);
        }

        return applicationDTO;
    }

    /**
     * Get applications by information
     *
     * @param processInstanceAppInfoRequestVM the Process instance application info
     * @return the applications list info
     */
    public Map<String,Object> getApplicationsByInfo(ProcessInstanceAppInfoRequestVM processInstanceAppInfoRequestVM) {
        log.debug("Request to get Application by processInstanceAppInfoRequestVM : {}", processInstanceAppInfoRequestVM);

        if (processInstanceAppInfoRequestVM == null) {
            throw new NullValueException("Process instance application info is null");
        }

        String status = processInstanceAppInfoRequestVM.getStatus() != null ? processInstanceAppInfoRequestVM.getStatus().value() : "";
        String emiratesId = processInstanceAppInfoRequestVM.getEmiratesId() != null ? processInstanceAppInfoRequestVM.getEmiratesId().trim() : "";
        Integer pageSize = processInstanceAppInfoRequestVM.getPageSize() != null ? processInstanceAppInfoRequestVM.getPageSize() : 10;
        Integer offset = processInstanceAppInfoRequestVM.getOffset() != null ? processInstanceAppInfoRequestVM.getOffset() : 0;
        String mobileNo = processInstanceAppInfoRequestVM.getMobileNo() != null ? processInstanceAppInfoRequestVM.getMobileNo().trim() : "";
        String licenseCategoryCode = processInstanceAppInfoRequestVM.getLicenseCategoryCode() != null ? processInstanceAppInfoRequestVM.getLicenseCategoryCode().trim() : "";

        // Find One By Info..
        List<ApplicationDTO> applicationsList = applicationMapper.toDto(applicationRepository.
                findByInfo(status, emiratesId, mobileNo,licenseCategoryCode,pageSize,offset)).
                stream().
                filter(applicationInfoFilterPredicate(
                        processInstanceAppInfoRequestVM.getApplicationDateFrom(),
                        processInstanceAppInfoRequestVM.getApplicationDateTo())).
                collect(Collectors.toList());

        applicationsList.forEach(applicationDTO -> {
            if (applicationDTO.getActivePhase() != null && !StringUtil.isBlank(applicationDTO.getActivePhase().value())) {
                MultilingualJsonType activePhaseDescription =
                        commonUtil.getDomainValueDescription(applicationDTO.getActivePhase().value(), PhaseType.DOMAIN_CODE);
                applicationDTO.setActivePhaseDescription(activePhaseDescription);
            }
        });

        Integer applicationsTotalCount = applicationRepository.findTotalCountByInfo(status, emiratesId, mobileNo,licenseCategoryCode);

        Map<String,Object> applicationsInfo = new HashMap<>();
        applicationsInfo.put("applications",applicationsList);
        applicationsInfo.put("applicationsTotalCount",applicationsTotalCount);

        return applicationsInfo;
    }

    /**
     * Application info filter predicate
     *
     * @param applicationDateFrom Application date from
     * @param applicationDateTo application date to
     * @return Application filter predicate
     */
    private Predicate<ApplicationDTO> applicationInfoFilterPredicate(LocalDate applicationDateFrom, LocalDate applicationDateTo) {
        if(applicationDateFrom != null && applicationDateTo != null) {
            return applicationDTO -> !applicationDTO.getCreatedDate().toLocalDate().isBefore(applicationDateFrom)
                    && !applicationDTO.getCreatedDate().toLocalDate().isAfter(applicationDateTo);
        } else if(applicationDateFrom != null) {
            return applicationDTO -> !applicationDTO.getCreatedDate().toLocalDate().isBefore(applicationDateFrom);
        } else if(applicationDateTo != null) {
            return applicationDTO -> !applicationDTO.getCreatedDate().toLocalDate().isAfter(applicationDateTo);
        } else {
            return applicationDTO -> true;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> findOne(Long applicationTypeId, String mobileNo, String birthDate) {
        log.debug("Request to get Application by application type id : {} , mobile no : {} , and birthdate : {}", applicationTypeId,mobileNo,birthDate);

        return applicationMapper.toDto(applicationRepository.findByMobileCriteria(StringUtil.getString(applicationTypeId),mobileNo,birthDate));
    }

    /**
     * Find application by emirates id and application type id criteria
     *
     * @param applicationTypeId application type id
     * @param eidNumber
     * @param eidExpiryDate
     *
     * Application which's fitted the passed criteria
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationDTO> findOneByEmiratesId(Long applicationTypeId, String eidNumber, String eidExpiryDate) {
        log.debug("Request to get Application by application Type Id eid {},  number: {}, eidExpiryDate {}", applicationTypeId,  eidNumber, eidExpiryDate);
        return applicationRepository.findOneByEmiratesId(applicationTypeId, eidNumber, eidExpiryDate)
            .map(applicationMapper::toDto);
    }

    /**
     * Find application by application Type RefNo and application type user Profile RefNo criteria
     *
     * @param applicationTypeRefNo application type id
     * @param userProfileRefNo
     * Application which's fitted the passed criteria
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationDTO> getByUserIdAndApplicationType(Long applicationTypeRefNo, Long userProfileRefNo) {
        log.debug("Request to get Application by application Type Id: {},userId {}", applicationTypeRefNo ,userProfileRefNo);
        return applicationRepository.findByApplicationType_idAndUserIdAndStatusIs(applicationTypeRefNo,userProfileRefNo,ApplicationStatus.UNDER_PROCESSING).map(applicationMapper::toDto);
    }

    /**
     * Delete the application by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Application : {}", id);
        applicationRepository.deleteById(id);
    }

    /**
     * Getter for application by status and before passed status date
     *
     * @param applicationStatus
     * @param statusDate
     *
     * @return List of application
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getByStatusAndStatusDateLessThan(ApplicationStatus applicationStatus , LocalDateTime statusDate) {
        log.debug("Request to get application by status and less than status date");
        if (applicationStatus == null || StringUtil.isBlank(applicationStatus.value())) {
            throw new SystemException("application status cannot be null");
        }

        if (statusDate == null) {
            throw new SystemException("application status date cannot be null");
        }

        return applicationMapper.toDto(applicationRepository.getByStatusAndStatusDateLessThan(applicationStatus, statusDate));
    }
}
