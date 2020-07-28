package ae.rta.dls.backend.aop.sys;

import ae.rta.dls.backend.common.util.DateUtil;
import ae.rta.dls.backend.common.util.NumberUtil;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Aspect
public class ConcurrencyVersionAspect {

    private static final String IF_MATCH_HEADER = "If-Match";

    @Autowired(required = false)
    private HttpServletRequest httpRequest;

    @Autowired(required = false)
    private HttpServletResponse httpResponse;

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(ae.rta.dls.backend.web.rest..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Before("applicationPackagePointcut() && @annotation(putMapping)")
    public void logBefore(JoinPoint joinPoint, PutMapping putMapping) {

        if (httpRequest == null) {
            return;
        }

        if (httpRequest.getHeader(IF_MATCH_HEADER) == null || httpRequest.getHeader(IF_MATCH_HEADER).trim().length() == 0) {
            throw new BadRequestAlertException("Missing If-Match header attribute", "", "");
        }

        if (joinPoint == null || joinPoint.getArgs() == null || joinPoint.getArgs().length == 0) {
            return;
        }

        for (Object obj : joinPoint.getArgs()) {

            if (obj == null) {
                continue;
            }

            LocalDateTime lastModifiedDateTime = DateUtil.getLocalDateTime(NumberUtil.toLong(httpRequest.getHeader(IF_MATCH_HEADER)));

            if (obj instanceof AbstractAuditingDTO) {
                AbstractAuditingDTO dto = (AbstractAuditingDTO) obj;
                dto.setLastModifiedDate(lastModifiedDateTime);
                return;
            }
        }
    }

    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "retVal")
    public void logAfter(Object retVal) {

        if (httpResponse == null || retVal == null) {
            return;
        }

        if (!(retVal instanceof ResponseEntity)) {
            return;
        }

        ResponseEntity responseEntity = (ResponseEntity) retVal;

        if (responseEntity.getBody() == null) {
            return;
        }

        if (!(responseEntity.getBody() instanceof AbstractAuditingDTO)) {
            return;
        }

        AbstractAuditingDTO dto = (AbstractAuditingDTO) responseEntity.getBody();

        if (dto == null || dto.getLastModifiedDate() == null) {
            return;
        }

        Long lastModifiedDateMilli = DateUtil.getMilliSeconds(dto.getLastModifiedDate());

        httpResponse.setHeader("ETag", lastModifiedDateMilli.toString());
    }
}
