package ae.rta.dls.backend.service.trn.impl;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.Base64Util;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.NumberUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.common.util.brm.calculation.fee.FeeCalculationRequest;
import ae.rta.dls.backend.common.util.brm.calculation.fee.ServiceFeeCriteriaVM;
import ae.rta.dls.backend.domain.enumeration.prd.LearningFileStatus;
import ae.rta.dls.backend.domain.enumeration.trn.HandbookType;
import ae.rta.dls.backend.domain.enumeration.trn.NocType;
import ae.rta.dls.backend.domain.enumeration.trn.ServiceCode;
import ae.rta.dls.backend.domain.type.ApplicationCriteriaJsonType;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.domain.type.ServiceDocumentJsonType;
import ae.rta.dls.backend.service.dto.sdm.LicenseCategoryDTO;
import ae.rta.dls.backend.domain.type.FeeDocumentJsonType;
import ae.rta.dls.backend.domain.type.LearningFileProductJsonType;
import ae.rta.dls.backend.service.dto.prd.CustomerInfoDTO;
import ae.rta.dls.backend.service.dto.prd.LearningFileDTO;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.dto.trn.LearningFileDetailDTO;
import ae.rta.dls.backend.service.dto.trn.ServiceRequestDTO;
import ae.rta.dls.backend.service.sdm.LicenseCategoryService;
import ae.rta.dls.backend.service.prd.LearningFileService;
import ae.rta.dls.backend.service.sys.BusinessRuleService;
import ae.rta.dls.backend.service.trn.ApplicationService;
import ae.rta.dls.backend.service.util.AbstractServiceRequest;
import ae.rta.dls.backend.service.util.ServiceRequestFactory;
import ae.rta.dls.backend.web.rest.errors.ApplicationNotFoundException;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.errors.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
@Transactional
public class LearningFileServiceTransactionImpl extends AbstractServiceRequest {

    /*
     * Class variables
     */

    /** Logger instance. */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private Base64Util base64Util;

    @Autowired
    private ServiceRequestFactory serviceRequestFactory;

    @Autowired
    private LicenseCategoryService licenseCategoryService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private BusinessRuleService businessRuleService;

    @Autowired
    private LearningFileService learningFileService;

    /*
     * Overridden template methods
     */

    @Override
    public void preCreate(ServiceRequestDTO serviceRequestDTO) {

        log.debug("Inside preCreate :: DrivingLearningFileServiceImpl");

        LearningFileDetailDTO learningFileDetailDTO = getServiceRequestDTO(serviceRequestDTO);

        Optional<ApplicationDTO> application = getApplication(serviceRequestDTO.getApplicationId());

        if (!application.isPresent()) {
            throw new ApplicationNotFoundException();
        }

        if (application.get().getApplicationCriteria().getCorporate() == null
            || StringUtil.isBlank(application.get().getApplicationCriteria().getCorporate().getArabicName())
            || StringUtil.isBlank(application.get().getApplicationCriteria().getCorporate().getEnglishName())) {
            throw new BadRequestAlertException("Missing corporate details");
        }


        checkNocAttachment(learningFileDetailDTO);

        learningFileDetailDTO.setStatus(LearningFileStatus.DRAFT);
        learningFileDetailDTO.setStatusDate(LocalDateTime.now());
        learningFileDetailDTO.setWithMaleInstructorPermit(false);
        learningFileDetailDTO.setRegistrationDate(null);
        learningFileDetailDTO.setLastRenewalDate(null);
        learningFileDetailDTO.setLastTestDate(null);
        learningFileDetailDTO.setPermitExpiryDate(null);
        learningFileDetailDTO.setLearningFileExpiryDate(null);
        learningFileDetailDTO.setLicenseCategoryCode(application.get().getApplicationCriteria().getLicenseCategoryCode());
        learningFileDetailDTO.setYardTest(application.get().getApplicationCriteria().getYardTest());
        learningFileDetailDTO.setRoadTest(application.get().getApplicationCriteria().getRoadTest());
        learningFileDetailDTO.setTheoryTest(application.get().getApplicationCriteria().getTheoryTest());
        learningFileDetailDTO.setTradeLicenseNo(application.get().getApplicationCriteria().getCorporate().getTradeLicenseNo());
        learningFileDetailDTO.setCorporateArabicName(application.get().getApplicationCriteria().getCorporate().getArabicName());
        learningFileDetailDTO.setCorporateEnglishName(application.get().getApplicationCriteria().getCorporate().getEnglishName());

        serviceRequestDTO.getServiceDocument().setParameters(learningFileDetailDTO);
    }

    @Override
    public void postCreate(ServiceRequestDTO serviceRequestDTO) {

        log.debug("Inside postCreate :: DrivingLearningFileServiceImpl");

        LearningFileDetailDTO learningFileDetailDTO = getServiceRequestDTO(serviceRequestDTO);

        Optional<ApplicationDTO> application = getApplication(serviceRequestDTO.getApplicationId());

        if (!application.isPresent()) {
            throw new ApplicationNotFoundException();
        }

        if(!StringUtil.isBlank(learningFileDetailDTO.getNocAttachment())) {
            ApplicationCriteriaJsonType criteria = new  ApplicationCriteriaJsonType();
            criteria.setNocType(NocType.FULFILLED_MANUAL);

            application.get().setApplicationCriteria(criteria);
            applicationService.update(application.get());
        }

        //Construct handbook service request
        ServiceRequestDTO handbookRequestDTO = new ServiceRequestDTO();
        handbookRequestDTO.setApplicationId(serviceRequestDTO.getApplicationId());
        handbookRequestDTO.setServiceCode(ServiceCode.BUY_HAND_BOOK.value());

        if (application.get().getApplicationCriteria() == null ||
            StringUtil.isBlank(application.get().getApplicationCriteria().getLicenseCategoryCode())) {
            throw new ValidationException("error.licenseCategory.required");
        }

        String licenseCategoryCode = application.get().getApplicationCriteria().getLicenseCategoryCode();
        Optional<LicenseCategoryDTO> licenseCategory = licenseCategoryService.findOne(licenseCategoryCode);
        if (!licenseCategory.isPresent() || licenseCategory.get().getHandbookType() == null) {
            throw new ValidationException("error.licenseCategory.required");
        }

        MultilingualJsonType licenseCategoryDescription = commonUtil.getDomainValueDescription(licenseCategory.get().getHandbookType(),
                                                                                               HandbookType.DOMAIN_CODE);
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        parameters.put("type",licenseCategory.get().getHandbookType());
        parameters.put("name",licenseCategoryDescription);

        ServiceDocumentJsonType serviceDocumentJsonType = new ServiceDocumentJsonType();
        serviceDocumentJsonType.setParameters(parameters);
        handbookRequestDTO.setServiceDocument(serviceDocumentJsonType);

        serviceRequestFactory.getServiceRequest(ServiceCode.BUY_HAND_BOOK.value()).create(handbookRequestDTO);
    }

    @Override
    public void preUpdate(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preUpdate :: DrivingLearningFileServiceImpl");
    }

    @Override
    public void postUpdate(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postUpdate :: DrivingLearningFileServiceImpl");
    }

    @Override
    public void preVerify(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preVerify :: DrivingLearningFileServiceImpl");
    }

    @Override
    public void postVerify(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postVerify :: DrivingLearningFileServiceImpl");

        LearningFileDetailDTO drivingLearningFileDTO = getServiceRequestDTO(serviceRequestDTO);

        if (drivingLearningFileDTO == null) {
            throw new BadRequestAlertException("Null Driving Learning FileD DTO");
        }

        ServiceFeeCriteriaVM serviceFeeCriteriaVM = new ServiceFeeCriteriaVM();
        serviceFeeCriteriaVM.setLicenseCategoryCode(drivingLearningFileDTO.getLicenseCategoryCode());

        FeeCalculationRequest feeCalculationRequest = new FeeCalculationRequest();
        feeCalculationRequest.setApplicationRef(serviceRequestDTO.getApplicationId());
        feeCalculationRequest.setServiceCode(NumberUtil.toLong(serviceRequestDTO.getServiceCode()));
        feeCalculationRequest.setExemptAllFees(Boolean.FALSE);
        feeCalculationRequest.setHandicapped(Boolean.FALSE);
        feeCalculationRequest.setServiceFeeCriteriaVM(serviceFeeCriteriaVM);

        FeeDocumentJsonType feeDocumentJsonType = businessRuleService.calculateFees(feeCalculationRequest);

        serviceRequestDTO.setFeeDocument(feeDocumentJsonType);
        serviceRequestDTO.setTotalFeeAmount(Double.valueOf(feeDocumentJsonType.getTotalAmount()));

    }

    @Override
    public void preConfirm(ServiceRequestDTO serviceRequestDTO, CustomerInfoDTO customerInfoDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preConfirm :: DrivingLearningFileServiceImpl");
    }

    @Override
    public void postConfirm(ServiceRequestDTO serviceRequestDTO, CustomerInfoDTO customerInfoDTO) {
        log.debug("Inside postConfirm :: DrivingLearningFileServiceImpl");

        // Load related application
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(serviceRequestDTO.getApplicationId());

        if (! applicationDTO.isPresent()) {
            log.error("There is no application for the given service request reference. {} ", serviceRequestDTO.getApplicationId());
            throw new SystemException("There is no application under the given application reference no.", serviceRequestDTO.getApplicationId());
        }

        LearningFileDTO learningFileDTO = new LearningFileDTO();
        learningFileDTO.setApplicationId(serviceRequestDTO.getApplicationId());
        learningFileDTO.setServiceRequestId(serviceRequestDTO.getId());
        learningFileDTO.setPersonaCategoryCode(applicationDTO.get().getPersona());
        learningFileDTO.setPersonaVersionCode(applicationDTO.get().getPersonaVersion());

        // Update service request status to be reflected on the new product
        LearningFileDetailDTO learningFileDetailDTO = getServiceRequestDTO(serviceRequestDTO);
        learningFileDetailDTO.setStatus(LearningFileStatus.UNDER_PROCESSING);
        learningFileDetailDTO.setStatusDate(LocalDateTime.now());
        learningFileDetailDTO.setPermitExpiryDate(LocalDate.now().plusMonths(3));
        learningFileDetailDTO.setRegistrationDate(LocalDateTime.now());


        // Construct new learning file product object
        LearningFileProductJsonType learningFileProductJsonType = new LearningFileProductJsonType();
        learningFileProductJsonType.setLearningFileDetailDTO(learningFileDetailDTO);
        learningFileProductJsonType.setCustomerInfoDTO(customerInfoDTO);
        learningFileDTO.setProductDocument(learningFileProductJsonType);

        learningFileService.save(learningFileDTO);
    }

    @Override
    public void preReject(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preReject :: DrivingLearningFileServiceImpl");
    }

    @Override
    public void postReject(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postReject :: DrivingLearningFileServiceImpl");
    }

    /**
     * Check Noc Attachment
     *
     * @param learningFileDetailDTO
     */
    private void checkNocAttachment(LearningFileDetailDTO learningFileDetailDTO) {

        if(learningFileDetailDTO.getNocAttachment() == null) {
            return;
        }

        //Validate Noc Attachment, size,mime type....
        base64Util.validateBase64Image(learningFileDetailDTO.getNocAttachment());

        String imageMimeType = base64Util.getByteArrayMimeType(base64Util.convertBase64ToByteArray(learningFileDetailDTO.getNocAttachment()));

        if (StringUtil.isBlank(imageMimeType) && learningFileDetailDTO.getNocAttachment().indexOf("application/pdf") > -1) {
            imageMimeType = "application/pdf";
        }

        String imageExtension = imageMimeType.substring(imageMimeType.indexOf('/')+1);

        learningFileDetailDTO.setNocAttachmentMimeType(imageExtension);
    }
}
