package ae.rta.dls.backend.config;

import ae.rta.dls.backend.aop.logging.AutomatedJobAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class AutomatedJobAspectConfiguration {

    @Bean
    public AutomatedJobAspect automatedJobAspect(Environment env) {
        return new AutomatedJobAspect(env);
    }
}
