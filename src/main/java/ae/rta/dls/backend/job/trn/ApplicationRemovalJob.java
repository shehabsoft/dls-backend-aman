package ae.rta.dls.backend.job.trn;

import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.NumberUtil;
import ae.rta.dls.backend.config.EnvironmentConstants;
import ae.rta.dls.backend.domain.enumeration.trn.ApplicationStatus;
import ae.rta.dls.backend.domain.enumeration.sys.ConfigurationKey;
import ae.rta.dls.backend.job.util.AbstractJob;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.trn.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Application Removal Automated Job
 *
 * @author Mohammad Qasim
 */
@Profile(EnvironmentConstants.EnvironmentFieldsConstants.SPRING_PROFILE_JOB)
@EnableScheduling
@Component
public class ApplicationRemovalJob extends AbstractJob {

    /** Application Service */
    public final ApplicationService applicationService;

    /** Common Util */
    private final CommonUtil commonUtil;

    /** Application Management Job Logger */
    private final Logger log = LoggerFactory.getLogger(ApplicationRemovalJob.class);

    /**
     * Application Removal Job Constructor
     *
     * @param applicationService
     * @param commonUtil
     */
    public ApplicationRemovalJob(ApplicationService applicationService , CommonUtil commonUtil) {
        this.applicationService = applicationService;
        this.commonUtil = commonUtil;
    }

    /**
     * Getter for the number of days allowed to remove the draft application from its last modification
     *
     * @return LocalDateTime
     */
    private LocalDateTime getApplicationRemovalDateBefore() {
        String removeApplicationBefore =
            commonUtil.getConfigurationValue(ConfigurationKey.REMOVE_DRAFT_APPLICATION.value());

        if (NumberUtil.toLong(removeApplicationBefore) <= 0) {

            //Default Object
            return LocalDateTime.now().minus(Duration.ofDays(1));
        }

        return LocalDateTime.now().minus(Duration.ofDays(NumberUtil.toLong(removeApplicationBefore)));
    }

    /**
     * This method tries to delete draft application before certain number of days
     */
    @Override
    @Scheduled(cron = "#{@automatedJobBeans.get('ae.rta.dls.backend.job.trn.ApplicationRemovalJob') != null and @automatedJobBeans.get('ae.rta.dls.backend.job.trn.ApplicationRemovalJob').cron != null ? @automatedJobBeans.get('ae.rta.dls.backend.job.trn.ApplicationRemovalJob').cron : @defaultCronExpression}")
    protected void run() {
        if (!canRun()) {
            return;
        }

        log.debug("Application Removal is starting ....");
        LocalDateTime statusDateBefore = getApplicationRemovalDateBefore();
        List<ApplicationDTO> expiredDraftApplicationList =
            applicationService.getByStatusAndStatusDateLessThan(ApplicationStatus.DRAFT,statusDateBefore);
        if (expiredDraftApplicationList == null || expiredDraftApplicationList.isEmpty()) {
            return;
        }

        log.debug("Trying to delete {} draft application", expiredDraftApplicationList.size());
        expiredDraftApplicationList.forEach(applicationDTO -> {
            if (applicationDTO != null && applicationDTO.getId() != null) {
                applicationService.delete(applicationDTO.getId());
            }
        });
    }
}
