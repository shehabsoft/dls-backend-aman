package ae.rta.dls.backend.config;

import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.service.dto.sys.AutomatedJobConfigDTO;
import ae.rta.dls.backend.service.sys.AutomatedJobConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Load automated job configuration and cache it when start spring context
 *
 * @author Mohammad Qasim
 */
@Configuration
public class AutomatedJobConfiguration {

    @Bean
    public String defaultCronExpression() {
        return Constants.DEFAULT_CRON_EXPRESSION;
    }

    @Bean
    public Long defaultFixedDelayValue() {
        return Constants.DEFAULT_FIXED_DELAY_VALUE;
    }

    @Bean
    public Long defaultFixedRateValue() {
        return Constants.DEFAULT_FIXED_RATE_VALUE;
    }

    @Bean
    public Long defaultInitialDelayValue() {
        return Constants.DEFAULT_INITIAL_DELAY_VALUE;
    }

    @Bean
    public Map<String, AutomatedJobConfigDTO> automatedJobBeans(AutomatedJobConfigService automatedJobConfigService) {

        Map<String , AutomatedJobConfigDTO> automatedJobConfigBeans = new HashMap<>();

        List<AutomatedJobConfigDTO> automatedJobConfigList = automatedJobConfigService.getActiveJobConfigList();
        if (automatedJobConfigList == null || automatedJobConfigList.isEmpty()) {
            return automatedJobConfigBeans;
        }

        for (AutomatedJobConfigDTO automatedJobConfigDTO : automatedJobConfigList) {
            if (automatedJobConfigDTO == null ||
                automatedJobConfigDTO.getId() == null ||
                StringUtil.isBlank(automatedJobConfigDTO.getJobName())) {
                continue;
            }

            automatedJobConfigBeans.put(automatedJobConfigDTO.getJobName(), automatedJobConfigDTO);
        }

        return automatedJobConfigBeans;
    }
}
