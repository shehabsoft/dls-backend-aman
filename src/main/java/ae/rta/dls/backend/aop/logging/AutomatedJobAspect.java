package ae.rta.dls.backend.aop.logging;

import ae.rta.dls.backend.common.util.DateUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.config.EnvironmentConstants;
import ae.rta.dls.backend.domain.enumeration.sys.JobStatus;
import ae.rta.dls.backend.job.util.AbstractJob;
import ae.rta.dls.backend.service.dto.sys.AutomatedJobAuditDTO;
import ae.rta.dls.backend.service.dto.sys.AutomatedJobConfigDTO;
import ae.rta.dls.backend.service.sys.AutomatedJobAuditService;
import ae.rta.dls.backend.service.sys.AutomatedJobConfigService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;


/**
 * The Automated Job Aspect
 * It only runs with the "job" profile.
 *
 * @author Tariq Abu Amireh
 */
@Aspect
public class AutomatedJobAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AutomatedJobConfigService automatedJobConfigService;

    @Autowired
    private AutomatedJobAuditService automatedJobAuditService;

    private final Environment env;


    public AutomatedJobAspect(Environment env) {
        this.env = env;
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("@annotation(scheduled)")
    @Profile(EnvironmentConstants.EnvironmentFieldsConstants.SPRING_PROFILE_JOB)
    public Object logAround(ProceedingJoinPoint joinPoint, Scheduled scheduled) throws Throwable {

        try {

            /* *************************************************************** */
            /* *********      Before execute the Automated Job  ****************/
            /* *************************************************************** */
            String jobName = ((AbstractJob) joinPoint.getTarget()).getClass().getName();

            // Save the Entity before the task is started
            AutomatedJobAuditDTO automatedJobAuditDTO = addAudit(jobName);


            /* *************************************************************** */
            /* **************************  Proceed  ****************************/
            /* *************************************************************** */
            Object result = joinPoint.proceed();


            /* *************************************************************** */
            /* ***********      After execute the Automated Job  **************/
            /* *************************************************************** */
            // Update the entity once the task is finished.
            updateAudit(automatedJobAuditDTO);

            return result;

        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            throw e;
        }
    }

    /**
     * Add Audit Record
     *
     * @param jobName : Job Name
     *
     * @return automatedJobAuditDTO
     */
    private AutomatedJobAuditDTO addAudit(String jobName) {
        if (StringUtil.isBlank(jobName)) {
            return null;
        }

        Optional<AutomatedJobConfigDTO> automatedJobConfigDTO = automatedJobConfigService.findByJobName(jobName);

        if (!automatedJobConfigDTO.isPresent()) {

            //Try to execute it by default value
            AutomatedJobAuditDTO automatedJobAuditDTO = new AutomatedJobAuditDTO(jobName,"Missing Job Configuration");
            automatedJobAuditDTO = automatedJobAuditService.save(automatedJobAuditDTO);

            return automatedJobAuditDTO;
        }

        if (automatedJobConfigDTO.get().getStatus() == null ||
            automatedJobConfigDTO.get().getStatus().value().equalsIgnoreCase(JobStatus.INACTIVE.value())) {

            return null;
        }

        AutomatedJobAuditDTO automatedJobAuditDTO = new AutomatedJobAuditDTO(automatedJobConfigDTO.get());
        automatedJobAuditDTO = automatedJobAuditService.save(automatedJobAuditDTO);

        // return DTO
        return automatedJobAuditDTO;
    }

    /**
     * Update Audit Record
     *
     * @param automatedJobAuditDTO : Automated Job Audit DTO
     */
    private void updateAudit(AutomatedJobAuditDTO automatedJobAuditDTO) {
        if (automatedJobAuditDTO == null) {
            return;
        }

        // Fill new Values
        automatedJobAuditDTO.setEndTime(LocalDateTime.now());
        Long diff = DateUtil.getSeconds(automatedJobAuditDTO.getEndTime())
            - DateUtil.getSeconds(automatedJobAuditDTO.getStartTime());
        automatedJobAuditDTO.setDurationInSeconds(diff);

        // Update DTO with the new values
        automatedJobAuditService.save(automatedJobAuditDTO);
    }
}
