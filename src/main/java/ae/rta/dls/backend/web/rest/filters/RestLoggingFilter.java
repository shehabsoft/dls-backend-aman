package ae.rta.dls.backend.web.rest.filters;

import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.sys.ConfigurationKey;
import ae.rta.dls.backend.service.dto.ws.rest.RestLogDTO;
import ae.rta.dls.backend.service.sys.ErrorLogService;
import ae.rta.dls.backend.service.ws.rest.RestLogService;
import ae.rta.dls.backend.web.rest.util.HttpServletResponseCopier;
import ae.rta.dls.backend.web.rest.util.MultiReadHttpServletRequest;
import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class RestLoggingFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(RestLoggingFilter.class);

    private final RestLogService restLogService;

    private final ErrorLogService errorLogService;

    private CommonUtil commonUtil;

    private final Tracer tracer;

    public RestLoggingFilter(RestLogService restLogService, ErrorLogService errorLogService, CommonUtil commonUtil, Tracer tracer) {
        this.restLogService = restLogService;
        this.errorLogService = errorLogService;
        this.tracer = tracer;
        this.commonUtil = commonUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponseCopier responseCopier = null;
        MultiReadHttpServletRequest multiReadRequest = null;
        RestLogDTO dto = null;

        try {

            String configurationValue =
                    commonUtil.getConfigurationValue(ConfigurationKey.ENABLE_REST_LOGGING.value());

            boolean loggingEnabled = !StringUtil.isEmpty(configurationValue) && configurationValue.equalsIgnoreCase(Boolean.TRUE.toString());

            if(!loggingEnabled) {
                // Proceed Do Filter..
                chain.doFilter(request, response);
                return;
            }

            /* Wrap the Request in order to read the inputstream multiple times */
            multiReadRequest = new MultiReadHttpServletRequest((HttpServletRequest) request);

            String requestBody = null;
            try (BufferedReader requestBufferReader = multiReadRequest.getReader()) {
                requestBody = requestBufferReader.lines().collect(Collectors.joining("\n"));
            } finally {
                // Nothing to do..
            }

            String requestUrl = ((HttpServletRequest) request).getRequestURL().toString();
            String httpMethod = ((HttpServletRequest) request).getMethod();

            responseCopier = new HttpServletResponseCopier((HttpServletResponse) response);

            // Get ApplicationId..
            Long applicationReferenceNo = commonUtil.getApplicationIdParam(requestBody,httpMethod,requestUrl);

            dto = new RestLogDTO();
            dto.setCorrelationId(tracer.currentSpan().context().traceIdString());
            dto.setApplicationId(applicationReferenceNo);
            dto.setRequestBody(requestBody);
            dto.setRequestUrl(requestUrl);
            dto.setHttpMethod(httpMethod);
            dto.setRestMode(RestLogDTO.RestMode.IN.value());

            // Add Rest Log..
            dto = restLogService.save(dto);

        } catch(Exception e) {
            log.error("Logging Request failed in RestLoggingFilter doFilter because of the following {}", e.getMessage());
            commonUtil.logError(e);
        }

        // Proceed Do Filter..
        if(multiReadRequest == null || responseCopier == null) {
            chain.doFilter(request, response);
            return;
        }

        chain.doFilter(multiReadRequest, responseCopier);

        try {
            String responseBody = new String(responseCopier.getCopy(), response.getCharacterEncoding());

            if(dto != null && dto.getId() != null) {
                dto.setResponseBody(responseBody);
                dto.setHttpStatus(((HttpServletResponse) response).getStatus());
                // Update Rest Log Response..
                restLogService.asyncSave(dto);
            }

        } catch(Exception e) {
            log.error("Logging Response Failed in RestLoggingFilter doFilter because of the following {}", e.getMessage());
            commonUtil.logError(e);
        }
    }

    @Override
    public void destroy() {
        // Filter Destroy..
    }
}
