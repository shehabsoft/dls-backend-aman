package ae.rta.dls.backend.service.trn.impl;

import ae.rta.dls.backend.common.util.Base64Util;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.NumberUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.prd.DrivingLicenseExperience;
import ae.rta.dls.backend.domain.enumeration.sys.ConfigurationKey;
import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;
import ae.rta.dls.backend.domain.type.CountryJsonType;
import ae.rta.dls.backend.domain.type.DrivingLicenseProductJsonType;
import ae.rta.dls.backend.domain.type.ForeignLicenseCategoryDetailsJsonType;
import ae.rta.dls.backend.service.dto.sdm.GlobalLicenseCategoryDTO;
import ae.rta.dls.backend.service.dto.sdm.LicenseCategoryDTO;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.dto.trn.ForeignLicenseDetailDTO;
import ae.rta.dls.backend.service.sdm.GlobalLicenseCategoryService;
import ae.rta.dls.backend.service.sdm.LicenseCategoryService;
import ae.rta.dls.backend.service.trf.ForeignLicenseTemplateViewService;
import ae.rta.dls.backend.service.dto.trf.ForeignLicenseTemplateViewDTO;
import ae.rta.dls.backend.service.dto.prd.CustomerInfoDTO;
import ae.rta.dls.backend.service.dto.prd.DrivingLicenseDTO;
import ae.rta.dls.backend.service.dto.trn.ServiceRequestDTO;
import ae.rta.dls.backend.service.prd.DrivingLicenseService;
import ae.rta.dls.backend.service.trn.ApplicationService;
import ae.rta.dls.backend.service.util.AbstractServiceRequest;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.errors.ValidationException;
import ae.rta.dls.backend.web.rest.errors.ValidationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ForeignLicenseServiceTransactionImpl extends AbstractServiceRequest {

    /** Default Maximum License Expiration period (in years) */
    public static final long DEFAULT_MAX_LICENSE_EXPIRATION_PERIOD = 10L;

    /*
     * Class variables
     */

    /** Logger instance. */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final List COUNTRIES_HAVE_STATES = new ArrayList<String>();

    static {
        COUNTRIES_HAVE_STATES.add("ARE");
        COUNTRIES_HAVE_STATES.add("AUS");
        COUNTRIES_HAVE_STATES.add("CAN");
        COUNTRIES_HAVE_STATES.add("USA");
    }

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ForeignLicenseTemplateViewService foreignLicenseTemplateViewService;

    @Autowired
    private DrivingLicenseService drivingLicenseService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private GlobalLicenseCategoryService globalLicenseCategoryService;

    @Autowired
    private LicenseCategoryService licenseCategoryService;

    @Autowired
    private Base64Util base64Util;

    /*
     * Overridden template methods
     */

    @Override
    public void preCreate(ServiceRequestDTO serviceRequestDTO) {

        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preCreate :: ForeignLicenseServiceHandler");

        ForeignLicenseDetailDTO foreignLicenseDetailDTO = getServiceRequestDTO(serviceRequestDTO);

        if (foreignLicenseDetailDTO.getIssuedFromCountryDetails() == null
                || foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode() == null
                || foreignLicenseDetailDTO.getIssuedFromCountryDetails().getName() == null
                || StringUtil.isBlank(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getName().getAr())
                || StringUtil.isBlank(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getName().getEn())) {

            throw new ValidationException("error.foreignLicense.missingIssuedFromCountry");
        }

        if (COUNTRIES_HAVE_STATES.contains(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode())) {
            if (foreignLicenseDetailDTO.getIssuedFromCountryDetails().getIssuedFromStateDetails() == null
                    || foreignLicenseDetailDTO.getIssuedFromCountryDetails().getIssuedFromStateDetails().getCode() == null
                    || foreignLicenseDetailDTO.getIssuedFromCountryDetails().getIssuedFromStateDetails().getName() == null
                    || StringUtil.isBlank(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getIssuedFromStateDetails().getName().getAr())
                    || StringUtil.isBlank(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getIssuedFromStateDetails().getName().getEn())) {
                throw new BadRequestAlertException("State is required for this country");
            }
        } else {
            if (foreignLicenseDetailDTO.getIssuedFromCountryDetails().getIssuedFromStateDetails() != null) {
                throw new BadRequestAlertException("state not allowed for this country");
            }
        }

        if (!CountryJsonType.COUNTRY_UAE.equals(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode())) {

            if (foreignLicenseDetailDTO.getValidity() == null) {
                throw new BadRequestAlertException("Missing held license validity");
            }
            if (foreignLicenseDetailDTO.getExperience() == null) {
                throw new BadRequestAlertException("Missing held license experience");
            }
            if (foreignLicenseDetailDTO.getDriverNationality() == null
                || StringUtil.isBlank(foreignLicenseDetailDTO.getDriverNationality().getCode())
                || foreignLicenseDetailDTO.getDriverNationality().getName() == null
                || StringUtil.isBlank(foreignLicenseDetailDTO.getDriverNationality().getName().getAr())
                || StringUtil.isBlank(foreignLicenseDetailDTO.getDriverNationality().getName().getEn())) {
                throw new BadRequestAlertException("Missing driver nationality details");
            }
            if (foreignLicenseDetailDTO.getCategoryDetails() == null ||
                foreignLicenseDetailDTO.getCategoryDetails().isEmpty()) {
                throw new ValidationException("error.foreignLicense.missingCategory");
            }

            for (int categoryIndex = 0 ; categoryIndex < foreignLicenseDetailDTO.getCategoryDetails().size(); categoryIndex++) {

                ForeignLicenseCategoryDetailsJsonType foreignLicenseCategoryDetailsJsonType =
                            foreignLicenseDetailDTO.getCategoryDetails().get(categoryIndex);

                if (foreignLicenseCategoryDetailsJsonType.getGlobalCategory() == null ||
                    foreignLicenseCategoryDetailsJsonType.getLocalCategory() == null ||
                    foreignLicenseCategoryDetailsJsonType.getGlobalCategory().getCode() == null ||
                    foreignLicenseCategoryDetailsJsonType.getGlobalCategory().getName() == null ||
                    StringUtil.isBlank(foreignLicenseCategoryDetailsJsonType.getGlobalCategory().getName().getEn()) ||
                    StringUtil.isBlank(foreignLicenseCategoryDetailsJsonType.getGlobalCategory().getName().getAr()) ||
                    foreignLicenseCategoryDetailsJsonType.getLocalCategory().getCode() == null ||
                    foreignLicenseCategoryDetailsJsonType.getLocalCategory().getName() == null ||
                    StringUtil.isBlank(foreignLicenseCategoryDetailsJsonType.getLocalCategory().getName().getEn()) ||
                    StringUtil.isBlank(foreignLicenseCategoryDetailsJsonType.getLocalCategory().getName().getAr())) {

                    throw new ValidationException("error.foreignLicense.missingCategory");
                }

                Optional<GlobalLicenseCategoryDTO> optionalGlobalLicenseCategoryDTO =
                        globalLicenseCategoryService.findOne(foreignLicenseCategoryDetailsJsonType.getGlobalCategory().getCode());

                if (! optionalGlobalLicenseCategoryDTO.isPresent()) {
                    throw new ValidationException("error.foreignLicense.missingCategory");
                }

                Optional<LicenseCategoryDTO> optionalLicenseCategoryDTO =
                        licenseCategoryService.findOne(foreignLicenseCategoryDetailsJsonType.getLocalCategory().getCode());

                if (! optionalLicenseCategoryDTO.isPresent()) {
                    throw new ValidationException("error.foreignLicense.missingCategory");
                }
            }

            Optional<ForeignLicenseTemplateViewDTO> heldLicenseTemplateViewDTO = null;

            if (foreignLicenseDetailDTO.getIssuedFromCountryDetails().getIssuedFromStateDetails() != null) {
                heldLicenseTemplateViewDTO = foreignLicenseTemplateViewService.
                    findOneByCountryCodeAndStateCode(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode(),
                        foreignLicenseDetailDTO.getIssuedFromCountryDetails().getIssuedFromStateDetails().getCode());
            } else {
                heldLicenseTemplateViewDTO = foreignLicenseTemplateViewService.
                    findOneByCountryCode(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode());
            }

            if (heldLicenseTemplateViewDTO.isPresent() && heldLicenseTemplateViewDTO.get().getId() != null) {
                foreignLicenseDetailDTO.setTemplateId(heldLicenseTemplateViewDTO.get().getId());
            }
        } else {
            if (StringUtil.isBlank(foreignLicenseDetailDTO.getLicenseNo())) {
                throw new BadRequestAlertException("License number is required for license from emirates");
            }
        }

        serviceRequestDTO.getServiceDocument().setParameters(foreignLicenseDetailDTO);
    }

    @Override
    public void postCreate(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postCreate :: ForeignLicenseServiceHandler");
    }

    @Override
    public void preUpdate(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preUpdate :: ForeignLicenseServiceHandler");

        if (serviceRequestDTO == null) {
            throw new BadRequestAlertException("service request DTO");
        }

        if (serviceRequestDTO.getApplicationId() == null) {
            throw new BadRequestAlertException("Service request application Id");
        }

        if (serviceRequestDTO.getServiceDocument() == null) {
            throw new BadRequestAlertException("Service request document not found");
        }

        ForeignLicenseDetailDTO foreignLicenseDetailDTO = getServiceRequestDTO(serviceRequestDTO);

        if (foreignLicenseDetailDTO == null) {
            throw new BadRequestAlertException("Driving License Detail DTO");
        }

        if (foreignLicenseDetailDTO.getIssuedFromCountryDetails() != null &&
            !COUNTRIES_HAVE_STATES.contains(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode())) {
            // Set state of selected country with NULL if it does not have state

            foreignLicenseDetailDTO.getIssuedFromCountryDetails().setIssuedFromStateDetails(null);
            serviceRequestDTO.getServiceDocument().setParameters(foreignLicenseDetailDTO);
        }

        //handle template value
        if (foreignLicenseDetailDTO.getIssuedFromCountryDetails() != null) {
            Optional<ForeignLicenseTemplateViewDTO> heldLicenseTemplateViewDTO = null;

            if (foreignLicenseDetailDTO.getIssuedFromCountryDetails().getIssuedFromStateDetails() != null) {
                heldLicenseTemplateViewDTO = foreignLicenseTemplateViewService.
                    findOneByCountryCodeAndStateCode(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode(),
                        foreignLicenseDetailDTO.getIssuedFromCountryDetails().getIssuedFromStateDetails().getCode());
            } else {
                heldLicenseTemplateViewDTO = foreignLicenseTemplateViewService.
                    findOneByCountryCode(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode());
            }

            if (heldLicenseTemplateViewDTO.isPresent() && heldLicenseTemplateViewDTO.get().getId() != null) {
                foreignLicenseDetailDTO.setTemplateId(heldLicenseTemplateViewDTO.get().getId());
            } else {
                foreignLicenseDetailDTO.setTemplateId(null);
            }
            serviceRequestDTO.getServiceDocument().setParameters(foreignLicenseDetailDTO);
        }

        Optional<ApplicationDTO> optionalApplicationDTO = applicationService.findOne(serviceRequestDTO.getApplicationId());
        if (!optionalApplicationDTO.isPresent()) {
            throw new BadRequestAlertException("Service request application not found");
        }

        if (!StringUtil.isBlank(foreignLicenseDetailDTO.getFrontSideImage())) {
            base64Util.validateBase64Image(foreignLicenseDetailDTO.getFrontSideImage());
        }

        if (!StringUtil.isBlank(foreignLicenseDetailDTO.getBackSideImage())) {
            base64Util.validateBase64Image(foreignLicenseDetailDTO.getBackSideImage());
        }

        if (optionalApplicationDTO.get().getActivePhase() == null ||
            StringUtil.isBlank(optionalApplicationDTO.get().getActivePhase().value()) ||
            !PhaseType.DRIVING_LEARNING_FILE_DS_AUDIT.value().equals(optionalApplicationDTO.get().getActivePhase().value())) {

            return;
        }

        if (StringUtil.isBlank(foreignLicenseDetailDTO.getLicenseNo())) {
            throw new ValidationException("error.foreignLicense.missingLicenseNo",ValidationType.WARNING);
        }

        if (foreignLicenseDetailDTO.getIssueDate() == null) {
            throw new ValidationException("error.foreignLicense.missingIssueDate",ValidationType.WARNING);
        }

        if (foreignLicenseDetailDTO.getExpiryDate() == null) {
            throw new ValidationException("error.foreignLicense.missingExpiryDate",ValidationType.WARNING);
        }

        if (foreignLicenseDetailDTO.getIssueDate().isAfter(LocalDate.now())) {
            throw new ValidationException("error.foreignLicense.invalidIssueDate",ValidationType.WARNING);
        }

        if (foreignLicenseDetailDTO.getExpiryDate().isBefore(foreignLicenseDetailDTO.getIssueDate())) {
            throw new ValidationException("error.foreignLicense.invalidExpiryDate",ValidationType.WARNING);
        }

        if (foreignLicenseDetailDTO.getExperience() == null || StringUtil.isBlank(foreignLicenseDetailDTO.getExperience().getValue())) {
            throw new ValidationException("error.foreignLicense.invalidDriverExperience",ValidationType.WARNING);
        }

        int yearsDiff = java.time.Period.between(foreignLicenseDetailDTO.getIssueDate(), LocalDate.now()).getYears();
        if (DrivingLicenseExperience.LESS_THAN_TWO.value().equals(foreignLicenseDetailDTO.getExperience().getValue()) && yearsDiff > 2) {
            throw new ValidationException("error.foreignLicense.invalidDriverExperience",ValidationType.WARNING);
        }

        if (DrivingLicenseExperience.MORE_THAN_FIVE.value().equals(foreignLicenseDetailDTO.getExperience().getValue()) && yearsDiff < 5) {
            throw new ValidationException("error.foreignLicense.invalidDriverExperience",ValidationType.WARNING);
        }

        if (DrivingLicenseExperience.BETWEEN_TWO_AND_FIVE.value().equals(foreignLicenseDetailDTO.getExperience().getValue()) &&
            (yearsDiff < 2 || yearsDiff > 5)) {
            throw new ValidationException("error.foreignLicense.invalidDriverExperience",ValidationType.WARNING);
        }

        if (foreignLicenseDetailDTO.getIssuedFromCountryDetails() == null ||
            StringUtil.isBlank(foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode())) {
            throw new ValidationException("error.foreignLicense.missingIssuedFromCountry");
        }

        Optional<DrivingLicenseDTO> optionalForeignLicenseDTO =
            drivingLicenseService.findOne(foreignLicenseDetailDTO.getLicenseNo(), foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode());

        if (optionalForeignLicenseDTO.isPresent()) {
            throw new ValidationException("error.foreignLicense.licenseFounded",ValidationType.WARNING);
        }

        Long maxLicenseExpirationPeriod = DEFAULT_MAX_LICENSE_EXPIRATION_PERIOD;
        String configValue = commonUtil.getConfigurationValue(ConfigurationKey.MAXIMUM_LICENSE_EXPIRATION_PERIOD.value());
        if (NumberUtil.isDigits(configValue)) {
            maxLicenseExpirationPeriod = NumberUtil.toLong(configValue);
        }

        if (foreignLicenseDetailDTO.getExpiryDate().plusYears(maxLicenseExpirationPeriod).isBefore(LocalDate.now())) {
            throw new ValidationException("error.foreignLicense.invalidLicenseExpiration",ValidationType.WARNING, StringUtil.getString(maxLicenseExpirationPeriod));
        }

        if (foreignLicenseDetailDTO.getCategoryDetails() == null ||
            foreignLicenseDetailDTO.getCategoryDetails().isEmpty()) {
            throw new ValidationException("error.foreignLicense.missingCategoryIssueDate");
        }


        for (int categoryIndex = 0; categoryIndex < foreignLicenseDetailDTO.getCategoryDetails().size(); categoryIndex++) {

            ForeignLicenseCategoryDetailsJsonType foreignLicenseCategoryDetailsJsonType =
                foreignLicenseDetailDTO.getCategoryDetails().get(categoryIndex);

            if (foreignLicenseCategoryDetailsJsonType.getGlobalCategory() == null ||
                foreignLicenseCategoryDetailsJsonType.getLocalCategory() == null ||
                foreignLicenseCategoryDetailsJsonType.getGlobalCategory().getCode() == null ||
                foreignLicenseCategoryDetailsJsonType.getGlobalCategory().getName() == null ||
                StringUtil.isBlank(foreignLicenseCategoryDetailsJsonType.getGlobalCategory().getName().getEn()) ||
                StringUtil.isBlank(foreignLicenseCategoryDetailsJsonType.getGlobalCategory().getName().getAr()) ||
                foreignLicenseCategoryDetailsJsonType.getLocalCategory().getCode() == null ||
                foreignLicenseCategoryDetailsJsonType.getLocalCategory().getName() == null ||
                StringUtil.isBlank(foreignLicenseCategoryDetailsJsonType.getLocalCategory().getName().getEn()) ||
                StringUtil.isBlank(foreignLicenseCategoryDetailsJsonType.getLocalCategory().getName().getAr())) {

                throw new ValidationException("error.foreignLicense.missingCategory");
            }

            if (foreignLicenseCategoryDetailsJsonType.getLicenseIssueDate() == null) {
                throw new ValidationException("error.foreignLicense.missingCategoryIssueDate");
            }

            if (foreignLicenseDetailDTO.getIssueDate().isAfter(foreignLicenseCategoryDetailsJsonType.getLicenseIssueDate())) {
                throw new ValidationException("error.foreignLicense.invalidCategoryIssueDate", ValidationType.WARNING);
            }

            if (foreignLicenseCategoryDetailsJsonType.getLicenseIssueDate().isAfter(LocalDate.now())) {
                throw new ValidationException("error.foreignLicense.invalidCategoryIssueDate.moreThanCurrentDate");
            }
        }
    }

    @Override
    public void postUpdate(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postUpdate :: ForeignLicenseServiceHandler");

        Optional<ApplicationDTO> application = applicationService.findOne(serviceRequestDTO.getApplicationId());

        if (!application.isPresent()) {
            throw new BadRequestAlertException("Service request application not found");
        }

        if (application.get().getActivePhase() != null && PhaseType.DRIVING_LEARNING_FILE_DS_AUDIT.equals(application.get().getActivePhase())) {
            application.get().getApplicationCriteria().setForeignLicenseReviewed(true);
            applicationService.update(application.get());
        }
    }

    @Override
    public void preVerify(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preVerify :: ForeignLicenseServiceHandler");
    }

    @Override
    public void postVerify(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postVerify :: ForeignLicenseServiceHandler");
    }

    @Override
    public void preConfirm(ServiceRequestDTO serviceRequestDTO, CustomerInfoDTO customerInfoDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preConfirm :: ForeignLicenseServiceHandler");

        if (serviceRequestDTO == null) {
            throw new BadRequestAlertException("service request DTO");
        }

        if (customerInfoDTO == null) {
            throw new BadRequestAlertException("customer info DTO");
        }

        if (serviceRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Service request Id");
        }

        if (serviceRequestDTO.getApplicationId() == null) {
            throw new BadRequestAlertException("Service request application Id");
        }

        ForeignLicenseDetailDTO foreignLicenseDetailDTO = getServiceRequestDTO(serviceRequestDTO);
        if (foreignLicenseDetailDTO == null) {
            throw new BadRequestAlertException("Driving License Detail DTO");
        }
    }

    @Override
    public void postConfirm(ServiceRequestDTO serviceRequestDTO, CustomerInfoDTO customerInfoDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postConfirm :: ForeignLicenseServiceHandler");

        ForeignLicenseDetailDTO foreignLicenseDetailDTO = getServiceRequestDTO(serviceRequestDTO);

        DrivingLicenseProductJsonType drivingLicenseProductJsonType = new DrivingLicenseProductJsonType();
        drivingLicenseProductJsonType.setCustomerInfoDTO(customerInfoDTO);

        // Check if customer has previous license or not
        DrivingLicenseDTO drivingLicenseDTO = null;
        Optional<DrivingLicenseDTO> optionalDrivingLicenseDTO = drivingLicenseService.findOne(StringUtil.getString(customerInfoDTO.getTrafficCodeNo()));
        if (optionalDrivingLicenseDTO.isPresent()) {
            drivingLicenseDTO = optionalDrivingLicenseDTO.get();
        }

        List<ForeignLicenseDetailDTO> foreignLicenseDetailDTOList = new ArrayList<>();
        if (drivingLicenseDTO == null) {
            drivingLicenseDTO = new DrivingLicenseDTO();
        } else {
            foreignLicenseDetailDTOList = drivingLicenseDTO.getProductDocument().getForeignLicenseDetailDTO();
        }

        drivingLicenseDTO.setServiceRequestId(serviceRequestDTO.getId());
        drivingLicenseDTO.setApplicationId(serviceRequestDTO.getApplicationId());

        foreignLicenseDetailDTOList.add(foreignLicenseDetailDTO);
        drivingLicenseProductJsonType.setForeignLicenseDetailDTO(foreignLicenseDetailDTOList);

        drivingLicenseDTO.setProductDocument(drivingLicenseProductJsonType);

        drivingLicenseService.save(drivingLicenseDTO);
    }

    @Override
    public void preReject(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preReject :: ForeignLicenseServiceHandler");
    }

    @Override
    public void postReject(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postReject :: ForeignLicenseServiceHandler");
    }
}
