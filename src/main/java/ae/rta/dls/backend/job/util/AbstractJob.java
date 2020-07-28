package ae.rta.dls.backend.job.util;

import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.sys.JobStatus;
import ae.rta.dls.backend.service.dto.sys.AutomatedJobConfigDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import java.util.Map;

/**
 * Abstract Automated Job
 *
 * @author Mohammad Qasim
 */
public abstract class AbstractJob {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Checks if automated job can run or not
     *
     * @return boolean
     */
    public boolean canRun() {
        if (StringUtil.isBlank(getClass().getName())) {
            return false;
        }

        Map<String,AutomatedJobConfigDTO> jobBeans = (Map<String,AutomatedJobConfigDTO>)applicationContext.getBean("automatedJobBeans");
        if (jobBeans.isEmpty()) {
            return false;
        }

        AutomatedJobConfigDTO bean = jobBeans.get(getClass().getName());

        return bean != null && bean.getStatus() != null && JobStatus.ACTIVE.value().equalsIgnoreCase(bean.getStatus().value());
    }

    /**
     * Reflect your automated job working by implementing it
     * from subclasses of <code>AbstractJob</code>
     *
     * To do new job you will use below template :
     *
     * @Scheduled({your job properties})
     * protected void run() {
     *     if (!canRun()) {
     *         return;
     *     }
     *
     *     {{Here your job business}}
     * }
     */
    protected abstract void run();
}
