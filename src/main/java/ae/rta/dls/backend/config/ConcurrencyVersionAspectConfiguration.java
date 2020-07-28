package ae.rta.dls.backend.config;

import ae.rta.dls.backend.aop.sys.ConcurrencyVersionAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ConcurrencyVersionAspectConfiguration {

    @Bean
    public ConcurrencyVersionAspect versionAspect() {
        return new ConcurrencyVersionAspect();
    }
}
