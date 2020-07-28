package ae.rta.dls.backend.service.trn.impl;

import ae.rta.dls.backend.client.rest.feign.NotificationProxy;
import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.common.Language;
import ae.rta.dls.backend.domain.enumeration.common.SystemType;
import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;
import ae.rta.dls.backend.domain.type.CorporateJsonType;
import ae.rta.dls.backend.service.dto.prd.LearningFileDTO;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.prd.LearningFileService;
import ae.rta.dls.backend.service.trn.ApplicationService;
import ae.rta.dls.backend.service.trn.TrnServiceFacade;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.vm.ntf.SendSmsNotificationRequestVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

/**
 * TrnServiceFacadeImpl  Implementation for managing Trn Facade service.
 */
@Service
@Transactional
public class TrnServiceFacadeImpl implements TrnServiceFacade {

    @Value("#{environment['frontend.context.root']}")
    private String frontendContextRoot;

    @Autowired
    private NotificationProxy notificationProxy;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private LearningFileService learningFileService;

    @Autowired
    private CommonUtil commonUtil;

    private final Logger log = LoggerFactory.getLogger(TrnServiceFacadeImpl.class);

    private static final String ENTITY_NAME = "dlsBackendApplication";

    /**
     * Update application
     *
     * @param applicationDTO Application DTO
     * @return application dto
     */
    @Override
    public ApplicationDTO updateApplication(ApplicationDTO applicationDTO){

        log.debug("REST request to update Application : {}", applicationDTO);
        if (applicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "application.id.empty");
        }

        // Send the contract to the customer
        sendContract(applicationDTO);

        // Update application info
        ApplicationDTO result = applicationService.update(applicationDTO);

        // Send Learning file confirmation
        sendLearningFileConfirmation(applicationDTO);

        return result;
    }

    /**
     * Send Contract for customer
     *
     * @param applicationDTO Application DTO
     */
    private void sendContract(ApplicationDTO applicationDTO) {
        log.info("Send the candidate contract");

        try {
            if(applicationDTO == null || applicationDTO.getActivePhase() == null) {
                return;
            }
            if(!applicationDTO.getActivePhase().equals(PhaseType.READY_FOR_CONTRACT_SIGN)) {
                return;
            }

            Optional<ApplicationDTO> application = applicationService.findOne(applicationDTO.getId());

            if(!application.isPresent() || application.get().getActivePhase() == null ||
                application.get().getActivePhase().equals(PhaseType.READY_FOR_CONTRACT_SIGN)) {
                return;
            }

            if(application.get().getApplicationCriteria() == null) {
                throw new SystemException("Invalid Criteria defined for this application");
            }
            if(StringUtil.isBlank(application.get().getApplicationCriteria().getMobileNo())) {
                throw new SystemException("No mobile number defined for this application");
            }

            Object[] arabicParameters = new Object[1];
            Object[] englishParameters = new Object[1];

            CorporateJsonType corporate = null;
            if (application.get().getApplicationCriteria().getCorporate() != null) {
                corporate = application.get().getApplicationCriteria().getCorporate();
            }

            arabicParameters[0] = corporate != null ? corporate.getArabicName() : "";
            englishParameters[0] = corporate != null ? corporate.getEnglishName() : "";

            String messageAr = messageSource.getMessage("bpm.confirmationMessage",arabicParameters, new Locale(Language.ar.value()));
            String messageEn = messageSource.getMessage("bpm.confirmationMessage",englishParameters, Locale.ENGLISH);

            String contractURL = frontendContextRoot + "/#/candidate/contract?appRefNo="+application.get().getId();

            messageAr = messageAr + "\n" + messageSource.getMessage("bpm.confirmationMessage.moreDetails",new Object[]{contractURL}, new Locale("ar"));
            messageEn = messageEn + "\n" + messageSource.getMessage("bpm.confirmationMessage.moreDetails",new Object[]{contractURL}, Locale.ENGLISH);

            String subjectAr = messageSource.getMessage("bpm.confirmationMessage.subject",null, new Locale(Language.ar.value()));
            String subjectEn = messageSource.getMessage("bpm.confirmationMessage.subject",null, Locale.ENGLISH);

            Language preferredLanguage = application.get().getApplicationCriteria().getPreferredLanguage();
            String message = preferredLanguage == Language.ar ? messageAr : messageEn;
            String subject = preferredLanguage == Language.ar ? subjectAr : subjectEn;

            SendSmsNotificationRequestVM sendSmsNotificationRequestVM = new SendSmsNotificationRequestVM();
            sendSmsNotificationRequestVM.setApplicationId(application.get().getId());
            sendSmsNotificationRequestVM.setMessage(message);
            sendSmsNotificationRequestVM.setMessageSubject(subject);
            sendSmsNotificationRequestVM.setLanguage(preferredLanguage);
            sendSmsNotificationRequestVM.setMobileNo(application.get().getApplicationCriteria().getMobileNo());
            sendSmsNotificationRequestVM.setSystemType(SystemType.DLS);

            // Send Sms Notification..
            notificationProxy.sendSmsNotification(sendSmsNotificationRequestVM);

        } catch(Exception ex) {
            log.error(String.format("ERROR While sending the candidate contract : %s" , ex.getMessage()));
            commonUtil.logError(ex);
        }
    }

    /**
     * Send sms notification once next active application phase is EYE_TEST (Temp solution)
     *
     * @param applicationDTO ApplicationDTO
     *
     */
    private void sendLearningFileConfirmation(ApplicationDTO applicationDTO) {
        log.info("Sending the Learning File Confirmation");

        if(applicationDTO == null || applicationDTO.getId() == null) {
            return;
        }

        try{

            if (applicationDTO.getActivePhase() == null ||
                !PhaseType.EYE_TEST.value().equals(applicationDTO.getActivePhase().value())) {
                return;
            }

            Optional<LearningFileDTO> learningFile = learningFileService.findActiveLearningFileByApplicationId(applicationDTO.getId());

            if(!learningFile.isPresent() || learningFile.get().getProductDocument() == null ||
                learningFile.get().getProductDocument().getCustomerInfoDTO() == null ||
                learningFile.get().getProductDocument().getCustomerInfoDTO().getTrafficCodeNo() == null) {

                return ;
            }

            Optional<ApplicationDTO> application = applicationService.findOne(applicationDTO.getId());

            if(!application.isPresent()) {
                throw new SystemException("Application not exists");
            }

            if(application.get().getApplicationCriteria() == null) {
                throw new SystemException("Invalid Criteria defined for this application");
            }

            if(StringUtil.isBlank(application.get().getApplicationCriteria().getMobileNo())) {
                throw new SystemException("No mobile number defined for this application");
            }

            Language preferredLanguage = application.get().getApplicationCriteria().getPreferredLanguage() != null ?
                                            application.get().getApplicationCriteria().getPreferredLanguage() : Language.en;
            Object[] parameters = new Object[]{StringUtil.getString(learningFile.get().getProductDocument().getCustomerInfoDTO().getTrafficCodeNo())};
            String message = null;
            String subject = null;
            if (Language.ar.value().equals(preferredLanguage.value())) {
                //arabic language
                message = messageSource.getMessage("bpm.uploadEyeTest",parameters, new Locale(Language.ar.value()));
                subject = messageSource.getMessage("bpm.uploadEyeTest.subject",null, new Locale(Language.ar.value()));
            } else {
                // english language
                message = messageSource.getMessage("bpm.uploadEyeTest",parameters, new Locale(Language.en.value()));
                subject = messageSource.getMessage("bpm.uploadEyeTest.subject",null, new Locale(Language.en.value()));
            }

            SendSmsNotificationRequestVM sendSmsNotificationRequestVM = new SendSmsNotificationRequestVM();
            sendSmsNotificationRequestVM.setApplicationId(applicationDTO.getId());
            sendSmsNotificationRequestVM.setMessage(message);
            sendSmsNotificationRequestVM.setMessageSubject(subject);
            sendSmsNotificationRequestVM.setLanguage(preferredLanguage);
            sendSmsNotificationRequestVM.setMobileNo(application.get().getApplicationCriteria().getMobileNo());
            sendSmsNotificationRequestVM.setSystemType(SystemType.DLS);

            // Send Sms Notification..
            notificationProxy.sendSmsNotification(sendSmsNotificationRequestVM);

        } catch(Exception ex) {
            log.error(String.format("ERROR While sending the candidate contract : %s" , ex.getMessage()));
            commonUtil.logError(ex);
        }
    }
}
