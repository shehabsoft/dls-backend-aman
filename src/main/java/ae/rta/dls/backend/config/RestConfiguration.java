package ae.rta.dls.backend.config;

import ae.rta.dls.backend.client.rest.util.BusinessRuleManagementInterceptor;
import ae.rta.dls.backend.client.rest.util.RestTemplateClientInterceptor;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.service.ws.rest.BrmRestLogService;
import ae.rta.dls.backend.service.sys.ApplicationConfigurationService;
import ae.rta.dls.backend.service.sys.ErrorLogService;
import ae.rta.dls.backend.service.ws.rest.RestLogService;
import ae.rta.dls.backend.web.rest.filters.RestLoggingFilter;
import brave.Tracer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Configuration
public class RestConfiguration {
    @Bean
    public FilterRegistrationBean<RestLoggingFilter> filterRegistrationBean(RestLogService restLogService,
                                                                            ErrorLogService errorLogService,
                                                                            CommonUtil commonUtil,
                                                                            Tracer tracer) {
        FilterRegistrationBean<RestLoggingFilter> registrationBean = new FilterRegistrationBean();
        RestLoggingFilter restLoggingFilter = new RestLoggingFilter(restLogService,errorLogService,commonUtil,tracer);

        registrationBean.setFilter(restLoggingFilter);
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.addUrlPatterns("/error");

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST,
                DispatcherType.FORWARD,
                DispatcherType.ERROR);

        registrationBean.setDispatcherTypes(dispatcherTypes);
        return registrationBean;
    }

    @Bean
    public RestTemplate restTemplate(Tracer tracer,
                                     RestLogService restLogService,
                                     ApplicationConfigurationService applicationConfigurationService,
                                     ErrorLogService errorLogService,
                                     CommonUtil commonUtil) {
        RestTemplate restTemplate
                = new RestTemplate(
                new BufferingClientHttpRequestFactory(
                        new SimpleClientHttpRequestFactory()
                )
        );

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new RestTemplateClientInterceptor(
                tracer,
                restLogService,
                applicationConfigurationService,
                errorLogService,
                commonUtil));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Bean
    public RestTemplate brmRestTemplate(@Value("${brm.context.root}") String brmContextRoot,
                                        Tracer tracer,
                                        BrmRestLogService brmRestLogService,
                                        ApplicationConfigurationService applicationConfigurationService,
                                        ErrorLogService errorLogService) {
        RestTemplate restTemplate
                = new RestTemplate(
                new BufferingClientHttpRequestFactory(
                        new SimpleClientHttpRequestFactory()
                )
        );

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new BusinessRuleManagementInterceptor(brmContextRoot,
                tracer,
                brmRestLogService,
                applicationConfigurationService,
                errorLogService));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
